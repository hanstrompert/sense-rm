package net.es.nsi.cs.lib;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.ogf.schemas.nsi._2013._12.connection.provider.ConnectionProviderPort;

/**
 * Utility classes for building clients
 *
 */
@Slf4j
public class ClientUtil {

  private final ConnectionProviderPort proxy;

  public ClientUtil(String url) {
    this.proxy = createProviderClient(url);
  }

  public ConnectionProviderPort getProxy() {
    return proxy;
  }

  /**
   * Creates a client class can be used to call provider at given URL
   *
   * @param url the URL of the provider to contact
   * @return the ConnectionProviderPort that you can use as the client
   */
  private ConnectionProviderPort createProviderClient(String url) {
    JaxWsProxyFactoryBean fb = new JaxWsProxyFactoryBean();
    fb.setAddress(url);

    Map<String, Object> props = fb.getProperties();
    if (props == null) {
      props = new HashMap<>();
    }
    props.put("jaxb.additionalContextClasses",
            new Class[]{
              org.ogf.schemas.nsi._2013._12.services.point2point.ObjectFactory.class,
              org.ogf.schemas.nsi._2013._12.services.types.ObjectFactory.class
            });
    fb.setProperties(props);

    fb.setServiceClass(ConnectionProviderPort.class);
    ConnectionProviderPort client = (ConnectionProviderPort) fb.create();

    return client;
  }
}
