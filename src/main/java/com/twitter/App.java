package com.twitter;

import java.util.List;

import twitter4j.MediaEntity;
import twitter4j.Status;

/**
 * To compile: mvn compile
 * To Run: $ mvn exec:java -Dexec.mainClass="com.twitter.App"
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        TweetReader twReader = new TweetReader();
      /*
        for (Status message: twReader.ReadHomeTimelineTweets()) {
          System.out.println(message.getCreatedAt() + " " +
              message.getId() + " " +
              message.getText() + " " +
              message.getGeoLocation());
        }
        for (Status status: twReader.ReadUserTimelineTweets("hackweekTweet")) {
          System.out.println(status.getCreatedAt() + " " +
              status.getId() + " " +
              status.getText() + " " +
              status.getGeoLocation());
          for (MediaEntity entity: status.getMediaEntities()) {
            System.out.println(entity.getMediaURL());
          }
        }

        List<Status> stasuses = twReader.GetTweetsNearBy("");
        System.out.println("number of tweets: " + stasuses.size());
        for (Status status: stasuses) {
          if (status.getGeoLocation() != null) {
            System.out.println(
                status.getUser().getScreenName() + " " +
                    status.getCreatedAt() + " " +
                    status.getText() + " " +
                    status.getGeoLocation());
          }
        }
        */
      twReader.GetLocationTweets();
    }
}
