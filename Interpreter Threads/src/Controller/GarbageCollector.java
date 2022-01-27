package Controller;

import Model.Containers.MyList;
import Model.Heap;
import Model.PrgState;
import Values.IValue;
import Values.RefValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GarbageCollector {

    Map<Integer, IValue> safeGarbageCollector(List<Integer> symbolTableAddresses, Heap heap){
        Set<HashMap.Entry<Integer, IValue>> heapSet = heap.entrySet();

        return heapSet.stream()
                .filter(e -> symbolTableAddresses.contains(e.getKey()))
                .collect(Collectors.toConcurrentMap(Map.Entry::getKey, HashMap.Entry::getValue));
    }

    public List<Integer> getAddressFromTables(MyList<PrgState> programs){
        return programs.stream()
                .map(prgState -> prgState.getSymbolTable().entrySet().stream())
                .flatMap(Function.identity())
                .collect(Collectors.toList())
                .stream()
                .filter(element -> element.getValue() instanceof RefValue)
                .map(element -> ((RefValue) element.getValue()).getAddress())
                .collect(Collectors.toList());
    }

    public List<Integer> getAddressFromSymbolTable(List<IValue> content){
        //will generate a list of values and will filter the reference values and return their addresses
        return content
                .stream()
                .filter(element -> element instanceof RefValue) //check if is RefValue
                .map(element -> ((RefValue) element).getAddress()) //map refvalue to its address
                .collect(Collectors.toList()); //collect in list
    }

    public List<Integer> addIndirections(List<Integer> addressesFromSymbolTable, Heap heapTable){
        boolean change = true;
        Set<Map.Entry<Integer, IValue>> heapSet = heapTable.entrySet();//get entry set that needs modifications
        List<Integer> newAddressList = addressesFromSymbolTable.stream().collect(Collectors.toList()); //copy of list in order to add indirections

        //we go through heapSet again and again and each time we add to the address list new indirection level
        // and new addresses which must NOT be deleted

        while (change){
            List<Integer> appendingList = null;
            change = false;
            appendingList = heapSet.stream()
                    .filter(e -> e.getValue() instanceof RefValue) //check if val in heap is RefValue so it can have indirections
                    .filter(e -> newAddressList.contains(e.getKey())) //check if address list contains ref to this
                    .map(e -> (((RefValue) e.getValue()).getAddress())) //map the reference to its address so we can add it
                    .filter(e -> !newAddressList.contains(e)) //check if the address list already has that reference from prev elems
                    .collect(Collectors.toList()); //collect to list

            if(!appendingList.isEmpty()){
                //we still have indirect references so we have to keep checking
                change = true;
                newAddressList.addAll(appendingList);
            }
        }
        return newAddressList;
    }


}
