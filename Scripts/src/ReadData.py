import glob
import sqlite3

array = glob.glob("C:/Users/andre/Desktop/QuestTemplates/*")
print(array)

for x in array:

    print("This is the "+x+" file")

    fileLines = open(x,"r").readlines()

    state = 0

    always_print = False
    title=""
    type=""
    description=""

    for y in fileLines:

        y=y.replace("\n","").replace("\r","")
        if(y.strip()==""):
            continue

        if state==0:
            if(y.startswith("@Title:")):
                title=y[7:].strip()
                state=1
        elif state==1:
            if(y.startswith("@Type:")):
                type=y[6:].strip()
                state=2
        elif state==2:
            if(always_print or y.startswith("@Description:")):
                description=y.replace("@Description:","").strip()
                state=3
        elif state==3:
            if(y.startswith("@Title:")):

                # print("Printing title: "+title)
                # print("Printing type: "+type)
                # print("Printing description: "+description)
                insertData(title,type,description)

                title=y[7:].strip()
                state=1
            else:
                description+=" "+y

    if state==3:
       insertData(title,type,description)



    def insertData(titleIn,typeIn,descriptionIn):
        try:
            connection = sqlite3.connect("../../QuestLog.sqlite")
            cursor = connection.cursor()

            SQLInsertQuery ="""INSERT INTO Quests (QuestName, QuestDescription, QuestType, IsUsed, MoraleBadResult, SupplyBadResult, MoraleNormalResult, SupplyNormalResult, MoraleGoodResult, SupplyGoodResult, MoraleDefaultResult, SupplyDefaultResult) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)"""

            count = cursor.execute(SQLInsertQuery,(titleIn,descriptionIn,typeIn,True,0,0,0,0,0,0,0,0))
            connection.commit()
            cursor.close()
        finally:
            if connection:
                connection.close()