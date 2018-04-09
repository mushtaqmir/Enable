package com.example.mushtaqmir.app4;

import java.util.Comparator;

/**
 * Created by Anshul.Kumar on 4/6/2018.
 */

public class ShortById implements Comparator<String[]> {


        @Override
        public int compare(String[] spaceProbes1, String[] spaceProbes2) {

            int spaceProbesID1=  Integer.parseInt(spaceProbes1[0]);
            int spaceProbesID2=  Integer.parseInt(spaceProbes2[0]);

            if(spaceProbesID1<spaceProbesID2){
                return 1;
            }
            else if(spaceProbesID2<spaceProbesID1){
                return -1;
            }
            else{
                return 0;
            }
        }

}
