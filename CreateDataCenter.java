package org.cloudbus.cloudsim.edf;

import java.util.ArrayList;
import java.util.List;
import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.provisioners.*;

public class CreateDataCenter {

  /**
   * Creates a datacenter with a specified number of hosts.
   * @param name Name of the datacenter.
   * @param numberOfHosts Number of hosts to create.
   * @return The created Datacenter instance.
   */
  public static Datacenter create(String name, int numberOfHosts) {
    // Host list
    List<Host> hostList = new ArrayList<>();

    // Host and PE parameters
    int numberOfPEs = 4; // Define the number of PEs per host
    int mips = 1000; // Define MIPS capacity per PE
    int ram = 2048; // 2 GB RAM per host
    long storage = 1000000; // 1 TB storage per host
    int bw = 10000; // Bandwidth per host

    // Create the specified number of hosts
    for (int hostId = 0; hostId < numberOfHosts; hostId++) {
      // Create PEs for each host
      List<Pe> peList = new ArrayList<>();
      for (int i = 0; i < numberOfPEs; i++) {
        peList.add(new Pe(i, new PeProvisionerSimple(mips))); // Each PE gets a unique ID
      }

      // Create the host with the PEs and other resources
      Host host = new Host(
        hostId,
        new RamProvisionerSimple(ram),
        new BwProvisionerSimple(bw),
        storage,
        peList, // List of PEs
        new VmSchedulerTimeShared(peList) // Time-Shared Scheduler
      );
      hostList.add(host);
    }

    // Datacenter characteristics
    String arch = "x86"; // System architecture
    String os = "Linux"; // Operating system
    String vmm = "Xen"; // Virtual Machine Monitor
    double timezone = 10.0; // Timezone for the datacenter
    double cost = 3.0; // Cost per second
    double costPerMem = 0.05; // Cost per MB of RAM
    double costPerStorage = 0.1; // Cost per MB of storage
    double costPerBw = 0.0; // Cost per Mbps of bandwidth

    DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
      arch,
      os,
      vmm,
      hostList,
      timezone,
      cost,
      costPerMem,
      costPerStorage,
      costPerBw
    );

    Datacenter datacenter = null;
    try {
      datacenter = new Datacenter(
        name,
        characteristics,
        new VmAllocationPolicySimple(hostList),
        new ArrayList<Storage>(),
        5.0
      );
    } catch (Exception e) {
      e.printStackTrace();
    }
    return datacenter;
  }
}
