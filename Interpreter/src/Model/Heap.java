package Model;



import Model.Containers.Dictionary;
import Model.Containers.IDictionary;
import Types.RefType;
import Values.IValue;
import Values.RefValue;
import Values.IValue;

import java.util.HashMap;
import java.util.Map;

public class Heap extends Dictionary<Integer, IValue> {

    public Heap() {
        super();
    }

    public int add(IValue value) {
        for (int i = 1; ; i++) {
            if (!this.has(i)) {
                add(i, value);
                return i;
            }
        }
    }

    public void removeCascade(int key) {
        HashMap<Integer, IValue> temporaryDict = this.deepCopy().Map;
        temporaryDict.remove(key);

        for (Map.Entry<Integer, IValue> entry : this.Map.entrySet())
            if (entry.getValue() instanceof RefValue)
                if (((RefValue) entry.getValue()).getAddress() == key)
                    removeCascadeCore(entry.getKey(), temporaryDict);

        this.Map = temporaryDict;

    }

    private void removeCascadeCore(int key, HashMap<Integer, IValue> temporaryDict){
        temporaryDict.remove(key);

        for (Map.Entry<Integer, IValue> entry : temporaryDict.entrySet())
            if (entry.getValue() instanceof RefValue)
                if (((RefValue) entry.getValue()).getAddress() == key)
                    removeCascadeCore(entry.getKey(), temporaryDict);
    }

    public void collectGarbage(Dictionary<String, IValue> symTable) {
        boolean keepCollecting = true;
        while (keepCollecting)
            keepCollecting = collectGarbageCore(symTable);
    }

    private boolean collectGarbageCore(Dictionary<String, IValue> symTable) {
        for (int address : this.Map.keySet()) {
            boolean found = false;
            for (Map.Entry<String, IValue> entry : symTable.Map.entrySet())
                if (entry.getValue().getType() instanceof RefType) {
                    int usedAddress = ((RefValue) entry.getValue()).getAddress();
                    if (usedAddress == address)
                        found = true;
                }
            if (!found) {
                removeCascade(address);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "{%s}".formatted(String.join(", ", this.Map.entrySet().stream().map(entry -> "%s -> %s".formatted(entry.getKey(), entry.getValue())).toArray(String[]::new)));
    }
}
