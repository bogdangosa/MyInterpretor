package Models.ProgramState;

import Containers.MyDictionary;
import Containers.MyIDictionary;
import Models.Value.StringValue;
import Models.Value.Value;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileTable implements IFileTable {
    private MyDictionary<StringValue, BufferedReader> file_table;

    public FileTable() {
        this.file_table = new MyDictionary<StringValue, BufferedReader>();
    }


    @Override
    public void put(StringValue key, BufferedReader value) {
        this.file_table.put(key, value);
    }

    @Override
    public BufferedReader lookUp(StringValue key) {
        return this.file_table.lookUp(key);
    }

    @Override
    public void update(StringValue key, BufferedReader value) {
        this.file_table.update(key, value);
    }

    @Override
    public boolean remove(StringValue key) {
        return this.file_table.remove(key);
    }

    @Override
    public boolean isDefined(StringValue key) {
        return this.file_table.isDefined(key);
    }

    @Override
    public List<String> toList() {
        List<String> list = new ArrayList<String>();
        for(Map.Entry<StringValue,BufferedReader> entity: file_table.getAll().entrySet()){
           list.add(entity.getKey().toString());
        }
        return list;
    }

    @Override
    public String toString() {
        String str = "File Table:";
        for(Map.Entry<StringValue,BufferedReader> entity: file_table.getAll().entrySet()){
            str += "\n"+entity.getKey();
        }
        return str;
    }
}
