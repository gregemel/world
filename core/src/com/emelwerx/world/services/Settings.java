package com.emelwerx.world.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Settings {
    private static final String file = ".world";
    private static final String leaderURL = "http://dreamlo.com/lb/PLfBGtHgG02wU0lSzVNrPAG0uQf3J3-UGzK1i7mXmmxA";
    private static final String request5 = "/pipe/5";
    private static boolean Paused;
    private static boolean soundEnabled = true;

    public static boolean isPaused() {
        return Paused;
    }

    public static void setPaused(boolean paused) {
        Paused = paused;
    }

    public static boolean isSoundEnabled() {
        return soundEnabled;
    }

    public static void setSoundEnabled(boolean soundEnabled) {
        Settings.soundEnabled = soundEnabled;
    }

    public static String getFile() {
        return file;
    }

    public static String getLeaderURL() {
        return leaderURL;
    }

    public static String getRequest5() {
        return request5;
    }

    public static void load(final Label[] leaderboardItems) {
        Net.HttpRequest requestBests = new Net.HttpRequest(Net.HttpMethods.GET);
        requestBests.setUrl(leaderURL + request5);
        Net.HttpResponseListener httpResponseListener = getHttpResponseListener(leaderboardItems);
        Gdx.net.sendHttpRequest(requestBests, httpResponseListener);
    }

    private static Net.HttpResponseListener getHttpResponseListener(final Label[] leaderboardItems) {
        return new Net.HttpResponseListener() {
                @Override
                public void handleHttpResponse(Net.HttpResponse httpResponse) {
                    System.out.println(httpResponse);
                    String string = String.format("%s", httpResponse.getResultAsString());
                    String scores[] = string.split("\n");
                    if (scores.length > 0)
                        for (int i = 0; i < scores.length; i++) {
                            String score[] = scores[i].split("\\|");
                            if (i == 0)
                                leaderboardItems[i].setText(String.valueOf(Integer.valueOf(score[score.length - 1]) + 1) + ")" + score[0] + ": " + score[1]);
                            else
                                leaderboardItems[i] = new Label(String.valueOf(Integer.valueOf(score[score.length - 1]) + 1) + ")" + score[0] + ": " + score[1], Assets.skin);
                        }
                }

                @Override
                public void failed(Throwable t) {
                    System.out.println(t);
                }

                @Override
                public void cancelled() {
                    System.out.println("Cancel");
                }
            };
    }

    public static void load() {
        try {
            FileHandle filehandle = Gdx.files.external(file);
            String[] strings = filehandle.readString().split("\n");
            soundEnabled = Boolean.parseBoolean(strings[0]);
            soundEnabled = true;
        } catch (Throwable e) {
        }
    }

    public static void save() {
        try {
            FileHandle filehandle = Gdx.files.external(file);
            filehandle.writeString(Boolean.toString(soundEnabled) + "\n", false);
        } catch (Throwable e) {
        }
    }

    public static void sendScore(int score) {
        Net.HttpRequest request = new Net.HttpRequest("GET");
        request.setUrl("http://dreamlo.com/lb/PLfBGtHgG02wU0lSzVNrPAG0uQf3J3-UGzK1i7mXmmxA/create/" + "world" + "/" + score);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
            }

            @Override
            public void failed(Throwable t) {
            }

            @Override
            public void cancelled() {
            }
        });
    }
}