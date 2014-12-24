package com.store.calling.api;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.beans.factory.FactoryBean;

public class WebTargetFactory implements FactoryBean<WebTarget>
{
    private String serverURL;

    public String getServerURL()
    {
        return serverURL;
    }

    public void setServerURL(String serverURL)
    {
        this.serverURL = serverURL;
    }

//    @Override
    public WebTarget getObject() throws Exception
    {
        WebTarget target = ClientBuilder.newClient().register(JacksonFeature.class).target(serverURL);
        return target;
    }

//    @Override
    public Class< ? > getObjectType()
    {

        return WebTarget.class;
    }

//    @Override
    public boolean isSingleton()
    {
        return true;
    }

}
