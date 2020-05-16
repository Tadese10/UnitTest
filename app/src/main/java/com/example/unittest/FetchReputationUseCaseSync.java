package com.example.unittest;

public class FetchReputationUseCaseSync {

    private FetchUserUseCaseSync fetchUserUseCaseSyncMock;

    public FetchReputationUseCaseSync(FetchUserUseCaseSync fetchUserUseCaseSyncMock) {

        this.fetchUserUseCaseSyncMock = fetchUserUseCaseSyncMock;
    }

    public FetchReputationResponse FetchReputation(String userId) {
        FetchReputationResponse response = new FetchReputationResponse(FetchReputationStatus.Failed, 0);
        FetchUserUseCaseSync.UseCaseResult result = fetchUserUseCaseSyncMock.fetchUserSync(userId);
        switch (result.getStatus()){
            case FAILURE:
            case NETWORK_ERROR:
                response.setFetchReputationStatus(FetchReputationStatus.Failed);
                response.setReputation(0);
                break;
            case SUCCESS:
                response.setFetchReputationStatus(FetchReputationStatus.Success);
                response.setReputation(1);
                break;
        }
        return response;
    }

    public class FetchReputationResponse{
        private FetchReputationStatus fetchReputationStatus;
        private int Reputation;

        public FetchReputationResponse(FetchReputationStatus fetchReputationStatus, int reputation) {
            this.fetchReputationStatus = fetchReputationStatus;
            Reputation = reputation;
        }

        public FetchReputationStatus getFetchReputationStatus() {
            return fetchReputationStatus;
        }

        public void setFetchReputationStatus(FetchReputationStatus fetchReputationStatus) {
            this.fetchReputationStatus = fetchReputationStatus;
        }

        public int getReputation() {
            return Reputation;
        }

        public void setReputation(int reputation) {
            Reputation = reputation;
        }
    }

    public  enum FetchReputationStatus{Failed, Success}
}
