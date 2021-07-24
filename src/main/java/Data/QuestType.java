package Data;

import java.util.HashMap;
import java.util.Map;

public enum QuestType {

    PEACE, SIEGE, CRISIS_MORALE, CRISIS_SUPPLY, CRISIS_END;

    private static final Map<Integer, QuestType> intToTypeMap = new HashMap<>();
    static {
        for (QuestType type : QuestType.values()) {
            intToTypeMap.put(type.ordinal(), type);
        }
    }

    public static QuestType fromInt(int i) {
        QuestType type = intToTypeMap.get(i);
        if (type == null)
            return QuestType.PEACE;
        return type;
    }

}
