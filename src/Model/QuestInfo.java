package Model;

public class QuestInfo<T> {

    private final T data;
    private final IQuest quest;

    public QuestInfo(T questModifierIn, IQuest questIn){
        data=questModifierIn;
        quest=questIn;
    }

    public T getData(){
        return data;
    }

    public IQuest getQuest(){
        return quest;
    }
}
