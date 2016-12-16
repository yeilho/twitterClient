package com.twitter;

import java.util.List;
import java.util.logging.Logger;

import twitter4j.FilterQuery;
import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;



/**
 * Created by iye on 12/14/16.
 */
public class TweetReader {
  private static Logger log = Logger.getLogger(TweetReader.class.getName());

  private static final String ACCESS_TOKEN = "";
  private static final String ACCESS_SECRET = "";
  private static final String CONSUMER_TOKEN = "";
  private static final String CONSUMER_SECRET = "";

  private Twitter twitter;
  private TwitterStream twitterStream;

  public TweetReader() {
    twitter = TwitterFactory.getSingleton();

    AccessToken accessToken = new AccessToken(ACCESS_TOKEN, ACCESS_SECRET);
    twitter.setOAuthConsumer(CONSUMER_TOKEN, CONSUMER_SECRET);
    twitter.setOAuthAccessToken(accessToken);
    twitterStream = new TwitterStreamFactory().getInstance();
    twitterStream.setOAuthConsumer(CONSUMER_TOKEN, CONSUMER_SECRET);
    twitterStream.setOAuthAccessToken(accessToken);
  }

  public List<Status> ReadHomeTimelineTweets() {
    try {
      return twitter.timelines().getHomeTimeline();
    } catch (TwitterException ex) {
      log.info(ex.getErrorMessage());
    }

    return null;
  }

  public List<Status> ReadUserTimelineTweets(String user) {
    try {
      return twitter.getUserTimeline(user);
    } catch (TwitterException ex) {
      log.info(ex.getErrorMessage());
    }
    return null;
  }

  public List<Status> GetTweetsNearBy(String searchString) {
    try {
      // seattle: 47.6062, -122.3321
      GeoLocation obj = new GeoLocation(47.6911733,-122.3789816);
      // central park NYC
      //GeoLocation obj = new GeoLocation(40.785091, -73.968285);
      Query query = new Query(searchString);
      query.setCount(150);
      query.setGeoCode(obj, 5, Query.Unit.km);
      QueryResult result = twitter.search(query);
      return result.getTweets();
    } catch (TwitterException ex) {
      log.info(ex.getErrorMessage());
      return null;
    }
  }

  public void GetLocationTweets() {
    double lat = 47.6911733;
    double longitude = -122.3789816;
    double lat1 = lat - 0.5;
    double longitude1 = longitude - 0.5;
    double lat2 = lat + 0.5;
    double longitude2 = longitude + 0.5;
    double[][] bb = {{longitude1, lat1}, {longitude2, lat2}};
    StatusListener listener = new StatusListener() {
      public void onStatus(Status status) {
        if (status.getGeoLocation() != null) {
          System.out.println(
              "@" + status.getUser().getScreenName() + " - " +
                  status.getText() + " " +
                  status.getGeoLocation());
        }
      }

      public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
        System.out.println("Got a status deletion notice id:" + statusDeletionNotice);
      }

      public void onTrackLimitationNotice(int i) {
        System.out.println("Got track limitation notice:" + i);
      }

      public void onScrubGeo(long userId, long upToStatusId) {
        System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
      }

      public void onStallWarning(StallWarning stallWarning) {
        System.out.println(stallWarning);
      }

      public void onException(Exception e) {
        e.printStackTrace();
      }
    };

    FilterQuery fq = new FilterQuery();
    fq.locations(bb);
    twitterStream.addListener(listener);
    twitterStream.filter(fq);
  }

}
