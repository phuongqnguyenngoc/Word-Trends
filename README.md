# Word-Trends
This project centers in the application of (priority queue) heap to store and analyze data

This program can read files which contain words and their frequency, put them into a priority queue heap, and return the n most common words. This is a link
https://drive.google.com/open?id=1QQ5RjfuQNc1IYwr2GHBcOljFezmrppc3  
to the output files of Word Frequences project which produces files of Key Value Pairs that represent words and its frequency. You can use these files or the file of your choice, but please format it in the way the example files are formatted.

If you use the example files (word count of Reddit comment files), the program also helps you navigate the trend of a list of certain words through 8 years. 

To find the N most common words, please run FindCommonWords followed by an integer that represents the number of words you want to get and the name(s) of the text file(s) you want to investigate. You'll get the result in the terminal

To find the trend of a list of words in different files (only applied for example file), run FindTrends followed by <BaseFilename> <FileNumberBegin> <FileNumberEnd> <Word1> <Word2>. For example, if you run 
    java FindTrends counts_reddit_comments_ 2008 2015 twitter snapchat facebook instagram
that means you want to look at the trend of the words: twitter, snapchat, facebook, instagram in files counts_reddit_comments_2008.txt upto count_reddit_comments_2015.txt
 
 !<img width="358" alt="image2019-11-18 12_21_48" src="https://user-images.githubusercontent.com/60492418/93058269-5b468980-f699-11ea-9dac-1ca2021c6026.png">
  

