package com.example.unittest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GetContactUseCaseSync {

    private List<Listener> listeners = new ArrayList<Listener>();

    public GetContactUseCaseSync(GetContactsHttpEndpoint getContactsHttpEndpoint) {
        this.getContactsHttpEndpoint = getContactsHttpEndpoint;
    }

    GetContactsHttpEndpoint getContactsHttpEndpoint;

    public void getContacts(String filteringString) {

        getContactsHttpEndpoint.getContacts(filteringString, new GetContactsHttpEndpoint.Callback() {

            @Override
            public void onGetContactsSucceeded(List<ContactSchema> cartItems) {
                for(Listener listener: listeners){
                    listener.getContactsSucceeded(cartItems);
                }
            }

            @Override
            public void onGetContactsFailed(GetContactsHttpEndpoint.FailReason failReason) {

                switch (failReason){
                    case GENERAL_ERROR:
                    case NETWORK_ERROR:
                        for(Listener listener: listeners){
                            listener.onGetContactsFailed(failReason);
                        }

                }

            }
        });
    }

    public void registerListener(Listener listener) {
        listeners.add(listener);
    }

    public interface Listener {

        void getContactsSucceeded(List<ContactSchema> contactSchemas);

        void onGetContactsFailed(GetContactsHttpEndpoint.FailReason capture);
    }
}
