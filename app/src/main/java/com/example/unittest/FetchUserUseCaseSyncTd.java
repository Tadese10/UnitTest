package com.example.unittest;

public class FetchUserUseCaseSyncTd {

    private FetchUserUseCaseSync fetchUserUseCaseSync;

    public  enum FetchUserStatus{
        Success,
        Failed,
        NetworkError
    }

    public FetchUserUseCaseSyncTd(FetchUserUseCaseSync fetchUserUseCaseSync) {
        this.fetchUserUseCaseSync = fetchUserUseCaseSync;
    }

    public FetchUserStatus FetchUserUseCaseSync(String userId) throws Exception {
       FetchUserUseCaseSync.UseCaseResult result =  fetchUserUseCaseSync.fetchUserSync(userId);
       FetchUserStatus fetchUserStatus = FetchUserStatus.Success;
       switch (result.getStatus()){
           case SUCCESS:
               fetchUserStatus = FetchUserStatus.Success;
               break;
           case FAILURE:
               fetchUserStatus = FetchUserStatus.Failed;
               break;
           case NETWORK_ERROR:
               fetchUserStatus = FetchUserStatus.NetworkError;
               break;
       }

       return fetchUserStatus;
    }
}
