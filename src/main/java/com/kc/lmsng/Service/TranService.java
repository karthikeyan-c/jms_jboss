package com.kc.lmsng.Service;

import com.kc.lmsng.Entity.Linkage;
import com.kc.lmsng.Entity.SubGroup;
import com.kc.lmsng.Entity.SubGroupMaster;
import com.kc.lmsng.Repository.LinkageRepository;
import com.kc.lmsng.Repository.SubGroupMasterRepository;
import com.kc.lmsng.Repository.SubGroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TranService {

    private final SubGroupRepository subGroupRepository;
    private final SubGroupMasterRepository subGroupMasterRepository;
    private final LinkageRepository linkageRepository;

    private final HashMap<Integer, List<String>> subGroupAcctList1 = new HashMap<>();
    private final HashMap<String, List<Integer>> subGroupAcctList2 = new HashMap<>();
    private final HashMap<Integer, Character> subGroupMasterList = new HashMap<>();
    private final HashMap<String,String> relationship = new HashMap<>();
    private final HashMap<String,List<String>> acctListMap = new HashMap<>();

    public HashMap<Integer, List<String>> getSubGroupAcctList1() {
        return subGroupAcctList1;
    }

    public HashMap<String, List<Integer>> getSubGroupAcctList2() {
        return subGroupAcctList2;
    }

    public HashMap<Integer, Character> getSubGroupMasterList() {
        return subGroupMasterList;
    }

    public HashMap<String, String> getRelationship() {
        return relationship;
    }

    public HashMap<String, List<String>> getAcctListMap() {
        return acctListMap;
    }

    public TranService(SubGroupRepository subGroupRepository, SubGroupMasterRepository subGroupMasterRepository, LinkageRepository linkageRepository) {
        this.subGroupRepository = subGroupRepository;
        this.subGroupMasterRepository = subGroupMasterRepository;
        this.linkageRepository = linkageRepository;
        //refreshAndBuild();
    }

    public synchronized void buildSubGroupToAcctMap() {
        List<SubGroup> subGroupAll = subGroupRepository.findAll();

        // Build SubGroup to Account Mapping.
        Map<Integer, List<SubGroup>> subGroupToAcctAll = subGroupAll.stream()
                .collect(Collectors.groupingBy(SubGroup::getSubGroupId)); //{ 1 : [<obj>A001,<obj>A002]}

        subGroupToAcctAll.forEach((key, value) -> subGroupAcctList1.put(key,
                        value.stream()
                        .map(SubGroup::getAcctId)
                        .collect(Collectors.toList()) //{1 : ["A001","A002"] }
        ));

        log.info("subGroupAcctList1 is " + subGroupAcctList1);

        // Move Parent of the subgroup to last.
        subGroupAcctList1.forEach((key, value) -> {
            List<String> connectingParent = value.stream()
                    .filter((acctId) -> !value.contains(getMaster(acctId)))
                    .collect(Collectors.toList());
            log.info("connectingParent is" + connectingParent);
            value.remove(connectingParent);
            log.info("value1 is" + connectingParent);
            value.addAll(connectingParent);
            log.info("value2 is" + connectingParent);
        });

        // Build Account to SubGroup Mapping.
        Map<String, List<SubGroup>> acctToSubGroupAll = subGroupAll.stream()
                .collect(Collectors.groupingBy(SubGroup::getAcctId));

        acctToSubGroupAll.forEach( (key, value) -> subGroupAcctList2.put(key,
                value.stream()
                .map(SubGroup::getSubGroupId)
                .collect(Collectors.toList()) // { "A001" : [ 1, 2] }
        ));

        // Build subGroup Master
        List<SubGroupMaster> subGroupMasterAll = subGroupMasterRepository.findAll();

        subGroupMasterAll.forEach( (subGroupMaster) -> {
            subGroupMasterList.put(subGroupMaster.getSubGroupId(), subGroupMaster.getGroupType()); // { 1 : N }
        });

        // Build Relationship
        List<Linkage> linkageAll = linkageRepository.findAll();

        linkageAll.forEach( (linkage -> {
            relationship.put( linkage.getChildAcct(), linkage.getParentAcct()); // { child: parent }
        }));

        log.info("subGroupAcctList1 is " + subGroupAcctList1);
        log.info("subGroupAcctList2 is " + subGroupAcctList2);
        log.info("subGroupMasterList is " + subGroupMasterList);
        log.info("relationship is " + relationship);
        log.info("processor is " + String.valueOf(Runtime.getRuntime().availableProcessors()));
    }

    @CachePut(value = "acctmap", key = "#acctId")
    public List<String> buildOneAcct(String acctId) {
        log.info("inside cachable new" + acctId);
        //if(subGroupAcctList1.isEmpty()) buildSubGroupToAcctMap();
        List<Integer> passSubGroupList = new ArrayList<>();
        List<String> acctList = new ArrayList<>();
        List<String> derivedAcctList = new ArrayList<>();

        acctList.add(acctId);
        derivedAcctList.add(acctId);
        //Derive accounts
        derivedAcctList.addAll(deriveAccts(acctList, passSubGroupList));
        //acctListMap.put(acctId, derivedAcctList);
        //return testService.putAcctMap(acctId,derivedAcctList);
        return derivedAcctList;
    }
    // passedSubGroupList = [];
    //for loop
    //       the one single account gets subgroup id list.
    //       subGroup.filter();
    //       subGroup.forEach{
    //       IF (subgroup not in passedSubGroupList){
    //       subgroup --> list of accounts.
    //       IF (type == netpooling)
    //          add to master list.
    //       ELSE
    //          list of account.filter(account's parent);
    //          account's parent only. ignore others.
    //          add to master list.
    //
    //       passedSubGroupList.add(subgroupId);
    //       }
    //       }
    //       acctList of For loop = new list
    // done
    public List<String> deriveAccts(List<String> acctList,
                                    List<Integer> passSubGroupList) {
        List<String> derivedAcctList = new ArrayList<>();

        acctList.forEach( (innerAcctId) -> {
            List<Integer> subGroupList = new ArrayList<>(subGroupAcctList2.get(innerAcctId));
            List<String> innerAcctList = new ArrayList<>();

            subGroupList.removeAll(passSubGroupList);

            subGroupList.forEach( (subGroup) -> {
                innerAcctList.clear();
                if (subGroupMasterList.get(subGroup) == 'N') {
                    innerAcctList.addAll(subGroupAcctList1.get(subGroup));
                }else{
                    innerAcctList.addAll(subGroupAcctList1.get(subGroup).stream()
                            .filter( acct -> acct.equals(getMaster(innerAcctId)))
                            .collect(Collectors.toList()));
                }
                passSubGroupList.add(subGroup);
                innerAcctList.remove(innerAcctId);
                derivedAcctList.addAll(innerAcctList);

                //recursive
                derivedAcctList.addAll(deriveAccts(innerAcctList,passSubGroupList));
            });
        });
        return derivedAcctList;
    }

    private String getMaster(String child) {
        return relationship.get(child);
    }

    @Cacheable(value = "acctmap", key = "#acctId")
    public List<String> getAcctMap(String acctId) {
        log.info("inside cachable" + acctId);
        buildSubGroupToAcctMap();
        return buildOneAcct(acctId);
    }
}
