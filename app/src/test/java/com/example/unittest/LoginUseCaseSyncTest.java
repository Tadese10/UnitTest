package com.example.unittest;

import com.example.unittest.authtoken.AuthTokenCache;
import com.example.unittest.eventbus.EventBusPoster;
import com.example.unittest.networking.LoginHttpEndpointSync;
import com.example.unittest.networking.NetworkErrorException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.theInstance;

public class LoginUseCaseSyncTest {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    LoginUseCaseSync SUT;
    LoginHttpEndpointSyncTd mLoginHttpEndpointSyncTd;
    AuthTokenCacheTd mAuthTokenCacheTd;
    EventBusPosterTd mEventBusPosterTd;
    private String AuthToken;

    @Before
    public void setUp() throws Exception {
        mLoginHttpEndpointSyncTd = new LoginHttpEndpointSyncTd();
        mAuthTokenCacheTd = new AuthTokenCacheTd();
        mEventBusPosterTd = new EventBusPosterTd();
        SUT = new LoginUseCaseSync(mLoginHttpEndpointSyncTd, mAuthTokenCacheTd, mEventBusPosterTd);
    }

    @Test
    public void loginSync_failed_UsernameWithEmptyPassword(){
        LoginUseCaseSync.UseCaseResult result = SUT.loginSync(USERNAME, "");
        Assert.assertThat(mLoginHttpEndpointSyncTd.mUsername, is(USERNAME));
        Assert.assertThat(mLoginHttpEndpointSyncTd.mPassword, is(""));
        Assert.assertThat(result, is(LoginUseCaseSync.UseCaseResult.FAILURE));
    }

    @Test
    public void loginSync_failed_emptyUsernameWithPasswordPassed(){
        LoginUseCaseSync.UseCaseResult result = SUT.loginSync("", PASSWORD);
        Assert.assertThat(mLoginHttpEndpointSyncTd.mPassword, is(PASSWORD));
        Assert.assertThat(mLoginHttpEndpointSyncTd.mUsername, is(""));
        Assert.assertThat(result, is(LoginUseCaseSync.UseCaseResult.FAILURE));
    }


    @Test
    public void loginSync_success_usernameAndPasswordPassed(){
        LoginUseCaseSync.UseCaseResult result = SUT.loginSync(USERNAME, PASSWORD);
        Assert.assertThat(mLoginHttpEndpointSyncTd.mPassword, is(PASSWORD));
        Assert.assertThat(mLoginHttpEndpointSyncTd.mUsername, is(USERNAME));
        Assert.assertThat(result, is(LoginUseCaseSync.UseCaseResult.SUCCESS));
    }

    @Test
    public void loginSync_success_authTokenCached(){
       SUT.loginSync(USERNAME, PASSWORD);
        Assert.assertThat(mAuthTokenCacheTd.getAuthToken(), is(AuthToken));
    }

   public class LoginHttpEndpointSyncTd implements LoginHttpEndpointSync{

       public String mUsername;
       private String mPassword;

       @Override
        public EndpointResult loginSync(String username, String password) throws NetworkErrorException {
            mUsername = username;
            mPassword = password;
           AuthToken = "authToken";
           if(mUsername == "" || mPassword == "")
               return new EndpointResult(EndpointResultStatus.AUTH_ERROR, AuthToken);
           return new EndpointResult(EndpointResultStatus.SUCCESS, AuthToken);
        }
    }

    public  class AuthTokenCacheTd implements AuthTokenCache{

        private String authToken;

        @Override
        public void cacheAuthToken(String authToken) {
            this.authToken = authToken;
        }

        @Override
        public String getAuthToken() {
            return authToken;
        }
    }

    public  class EventBusPosterTd implements EventBusPoster{

        Object event;
        @Override
        public void postEvent(Object event) {
            this.event = event;
        }
    }
}