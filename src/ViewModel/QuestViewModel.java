package ViewModel;

import Model.IQuest;

public class QuestViewModel {

    private final IQuest quest;
    private int modifier;

    public QuestViewModel(IQuest questIn){
        quest=questIn;
    }

    public String getName(){
        return quest.getQuestName();
    }

    public String getDescription(){
        return quest.getQuestDescription();
    }

    public int getModifier(){
        return modifier;
    }

    public void setModifier(int value){
        modifier=value;
        if (modifier<-10){
            modifier=-10;
        } else if(modifier>30){
            modifier=30;
        }
    }

    public IQuest getQuest(){
        return quest;
    }
}
