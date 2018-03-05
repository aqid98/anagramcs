/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import java.lang.*;
import java.util.Random;


public class AnagramDictionary {
   private ArrayList<String> wordList;

    private HashSet<String> wordSet;
    private HashMap<String, ArrayList<String>> lettersToWord;
    private HashMap<Integer, ArrayList<String>> sizeToWords;
    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private int wordLength=DEFAULT_WORD_LENGTH;



    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line,key;
        wordList = new ArrayList<>();
        lettersToWord = new HashMap<>();
        wordSet=new HashSet<>();
        sizeToWords=new HashMap<>();



        while((line = in.readLine()) != null) {
            String word = line.trim();

             wordList.add(word);
            wordSet.add(word);
            ArrayList<String> addtolist = new ArrayList();
            key = sortLetters(word);
            if(!lettersToWord.containsKey(key)){
                addtolist.add(word);
                lettersToWord.put(key,addtolist);
            }
            else{
                addtolist = (ArrayList) lettersToWord.get(key);
                addtolist.add(word);
                lettersToWord.put(key, addtolist);
            }
        }
    }

    public boolean isGoodWord(String word, String base) {
        if(word.toLowerCase().contains(base.toLowerCase()))
            return  false;
        if(wordSet.contains(word))
                return true;

        return false;

    }



    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for (char c = 'a'; c <= 'z'; c++) {
            String sortedWord = sortLetters(word+c);
            for (int i=0; lettersToWord.containsKey(sortedWord) && i<lettersToWord.get(sortedWord).size(); i++) {
                if (isGoodWord(lettersToWord.get(sortedWord).get(i), word))
                    result.add(lettersToWord.get(sortedWord).get(i));
            }
        }
        return result;
    }


    public String pickGoodStarterWord() {
            while(true){
                int randomNum=random.nextInt(wordList.size());
                String randomWord=wordList.get(randomNum);
                ArrayList<String>result=(ArrayList) lettersToWord.get(sortLetters(randomWord));
                if(randomWord.length()<=MAX_WORD_LENGTH&&result.size()>=MIN_NUM_ANAGRAMS)
                    return (String)wordList.get(randomNum);
            }

    }

    public String sortLetters(String word)
    {
        char[] sortedWord=word.toCharArray();
        Arrays.sort(sortedWord);
        return new String(sortedWord);
    }
}
