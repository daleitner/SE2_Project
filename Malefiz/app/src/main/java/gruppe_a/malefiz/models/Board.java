package gruppe_a.malefiz.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by worm on 13.04.16.
 */
public class Board {
    private List<Field> fields;

    public Board (){
        InitializeFields();
    }

    private void InitializeFields()
    {
        this.fields = new ArrayList<Field>();
        for(int i = 1; i<133; i++)
        {
            Field field = new Field(i);
            fields.add(field);
        }

        for(int j = 5; j < 21; j++)
        {
            LinkFields(fields.get(j), fields.get(j+1));
        }

        for(int j = 27; j < 43; j++)
        {
            LinkFields(fields.get(j), fields.get(j+1));
        }

        for(int j = 48; j < 60; j++)
        {
            LinkFields(fields.get(j), fields.get(j+1));
        }

        for(int j = 63; j < 71; j++)
        {
            LinkFields(fields.get(j), fields.get(j+1));
        }

        for(int j = 74; j < 78; j++)
        {
            LinkFields(fields.get(j), fields.get(j+1));
        }

        for(int j = 80; j < 96; j++)
        {
            LinkFields(fields.get(j), fields.get(j+1));
        }

        for(int j = 99; j < 115; j++)
        {
            LinkFields(fields.get(j), fields.get(j+1));
        }

        LinkFields(fields.get(22), fields.get(27));
        LinkFields(fields.get(22), fields.get(5));

        LinkFields(fields.get(23), fields.get(31));
        LinkFields(fields.get(23), fields.get(9));

        LinkFields(fields.get(24), fields.get(13));
        LinkFields(fields.get(24), fields.get(35));

        LinkFields(fields.get(25), fields.get(39));
        LinkFields(fields.get(25), fields.get(17));

        LinkFields(fields.get(44), fields.get(29));
        LinkFields(fields.get(44), fields.get(48));

        LinkFields(fields.get(45), fields.get(33));
        LinkFields(fields.get(45), fields.get(52));

        LinkFields(fields.get(46), fields.get(37));
        LinkFields(fields.get(46), fields.get(56));

        LinkFields(fields.get(47), fields.get(60));
        LinkFields(fields.get(47), fields.get(41));

        LinkFields(fields.get(61), fields.get(63));
        LinkFields(fields.get(61), fields.get(50));

        LinkFields(fields.get(62), fields.get(71));
        LinkFields(fields.get(62), fields.get(58));

        LinkFields(fields.get(72), fields.get(74));
        LinkFields(fields.get(72), fields.get(65));

        LinkFields(fields.get(73), fields.get(78));
        LinkFields(fields.get(73), fields.get(69));

        LinkFields(fields.get(79), fields.get(88));
        LinkFields(fields.get(79), fields.get(76));

        LinkFields(fields.get(97), fields.get(99));
        LinkFields(fields.get(97), fields.get(80));

        LinkFields(fields.get(98), fields.get(115));
        LinkFields(fields.get(98), fields.get(96));

        LinkFieldsOneWay(fields.get(107), fields.get(116));

        //Peg Fields
        //red
        LinkFieldsOneWay(fields.get(1), fields.get(7));
        LinkFieldsOneWay(fields.get(117), fields.get(7));
        LinkFieldsOneWay(fields.get(118), fields.get(7));
        LinkFieldsOneWay(fields.get(119), fields.get(7));
        LinkFieldsOneWay(fields.get(120), fields.get(7));
        //green
        LinkFieldsOneWay(fields.get(2), fields.get(11));
        LinkFieldsOneWay(fields.get(121), fields.get(11));
        LinkFieldsOneWay(fields.get(122), fields.get(11));
        LinkFieldsOneWay(fields.get(123), fields.get(11));
        LinkFieldsOneWay(fields.get(124), fields.get(11));
        //yellow
        LinkFieldsOneWay(fields.get(3), fields.get(15));
        LinkFieldsOneWay(fields.get(125), fields.get(15));
        LinkFieldsOneWay(fields.get(126), fields.get(15));
        LinkFieldsOneWay(fields.get(127), fields.get(15));
        LinkFieldsOneWay(fields.get(128), fields.get(15));
        //blue
        LinkFieldsOneWay(fields.get(4), fields.get(19));
        LinkFieldsOneWay(fields.get(129), fields.get(19));
        LinkFieldsOneWay(fields.get(130), fields.get(19));
        LinkFieldsOneWay(fields.get(131), fields.get(19));
        LinkFieldsOneWay(fields.get(132), fields.get(19));
    }

    private void LinkFields(Field source, Field target) {
        source.getChildren().add(target);
        target.getChildren().add(source);
    }

    private void LinkFieldsOneWay(Field source, Field target) {
        source.getChildren().add(target);
    }
}
