/*
 * SENSE Resource Manager (SENSE-RM) Copyright (c) 2016, The Regents
 * of the University of California, through Lawrence Berkeley National
 * Laboratory (subject to receipt of any required approvals from the
 * U.S. Dept. of Energy).  All rights reserved.
 *
 * If you have questions about your rights to use or distribute this
 * software, please contact Berkeley Lab's Innovation & Partnerships
 * Office at IPO@lbl.gov.
 *
 * NOTICE.  This Software was developed under funding from the
 * U.S. Department of Energy and the U.S. Government consequently retains
 * certain rights. As such, the U.S. Government has been granted for
 * itself and others acting on its behalf a paid-up, nonexclusive,
 * irrevocable, worldwide license in the Software to reproduce,
 * distribute copies to the public, prepare derivative works, and perform
 * publicly and display publicly, and to permit other to do so.
 *
 */
package net.es.sense.rm.driver.nsi.cs.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import org.ogf.schemas.nsi._2013._12.connection.types.LifecycleStateEnumType;
import org.ogf.schemas.nsi._2013._12.connection.types.ProvisionStateEnumType;
import org.ogf.schemas.nsi._2013._12.connection.types.ReservationStateEnumType;

/**
 *
 * @author hacksaw
 */
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Data
@Entity
@Table(name = "reservations")
public class Reservation implements Serializable {

  @Id
  @GeneratedValue
  long id;

  // The time I first discovered this version of the connection.
  @Basic(optional = false)
  long discovered;

  // The providerNSA hosting this conenction.
  @Basic(optional = false)
  String providerNsa;

  // Optional global reservation identifier.
  @Basic(optional = true)
  String globalReservationId;

  @Basic(optional = true)
  String description;

  // Connection identifier of this connection on the uPA.
  @Basic(optional = false)
  String connectionId;

  // The network hosting this connection.
  @Basic(optional = true)
  String topologyId;

  // The serviceType of the connection.
  @Basic(optional = true)
  String serviceType;

  // The schedule parameters for this reservation.
  long startTime;
  long endTime;

  // Reservation state information.
  ReservationStateEnumType reservationState;
  ProvisionStateEnumType provisionState;
  LifecycleStateEnumType lifecycleState;

  boolean dataPlaneActive = false;

  // Version of the reservation on the uPA.
  long version;

  // The service element encoded in a string.
  @Lob
  @Basic(fetch = FetchType.LAZY, optional = true)
  String service;

  @Override
  public String toString() {
    return "Reservation{" +
            "id=" + id +
            ", discovered=" + discovered +
            ", providerNsa='" + providerNsa + '\'' +
            ", globalReservationId='" + globalReservationId + '\'' +
            ", description='" + description + '\'' +
            ", connectionId='" + connectionId + '\'' +
            ", topologyId='" + topologyId + '\'' +
            ", serviceType='" + serviceType + '\'' +
            ", startTime=" + startTime +
            ", endTime=" + endTime +
            ", reservationState=" + reservationState +
            ", provisionState=" + provisionState +
            ", lifecycleState=" + lifecycleState +
            ", dataPlaneActive=" + dataPlaneActive +
            ", version=" + version +
            ", service='" + service + '\'' +
            '}';
  }

  public boolean diff(Reservation r) {
    if (providerNsa != null && r.getProviderNsa() != null && !providerNsa.equals(r.getProviderNsa())) {
      return true;
    }

    if (globalReservationId != null && r.getGlobalReservationId() != null
            && !globalReservationId.equals(r.getGlobalReservationId())) {
      return true;
    }

    if (description != null && r.getDescription() != null
            && !description.equals(r.getDescription())) {
      return true;
    }

    if (connectionId != null && r.getConnectionId() != null
            && !connectionId.equals(r.getConnectionId())) {
      return true;
    }

    if (topologyId != null && r.getTopologyId() != null
            && !topologyId.equals(r.getTopologyId())) {
      return true;
    }

    if (serviceType != null && r.getServiceType() != null
            && !serviceType.equals(r.getServiceType())) {
      return true;
    }

    if (startTime != r.getStartTime() || endTime != r.getEndTime()) {
      return true;
    }

    if (reservationState != r.getReservationState()
            || provisionState != r.getProvisionState()
            || lifecycleState != r.getLifecycleState()
            || dataPlaneActive != r.isDataPlaneActive()) {
      return true;
    }

    if (version != r.getVersion()) {
      return true;
    }

    return false;
  }
}
