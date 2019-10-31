package resultMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TerminalResutSet {
    private Map<String, Map<String, String>> map;

    public TerminalResutSet() {
        this.map = new HashMap<String, Map<String, String>>();
    }

    public Map<String, Map<String, String>> getMap() {
        return map;
    }

    public void setMap(Map<String, Map<String, String>> map) {
        this.map = map;
    }

    public void addTerminal(String terminalID, String key, String value) {
        if (isExistTerminalMap(terminalID)){
            Map<String, String> map = new HashMap<String, String>();

        }

    }

    public boolean isExistTerminalMap(String terminalID) {
        if (map.get(terminalID) != null) {
            return false;
        }
        return true;
    }
}
