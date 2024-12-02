package org.cloudbus.cloudsim.edf;

import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.provisioners.*;

import java.util.ArrayList;
import java.util.List;

public class CreateDataCenter {
  public static Datacenter create(String name) {
    // Host list
    List < Host > hostList = new ArrayList < > ();

    // PE and Host parameters
    int numberOfPEs = 4; // Define the number of PEs
    int mips = 1000; // Define MIPS capacity per PE
    int ram = 2048; // 2 GB RAM
    long storage = 1000000; // 1 TB Storage
    int bw = 10000; // Bandwidth

    // Create Processing Elements (PEs)
    List < Pe > peList = new ArrayList < > ();
    for (int i = 0; i < numberOfPEs; i++) {
      peList.add(new Pe(i, new PeProvisionerSimple(mips))); // Each PE gets a unique ID
    }

    // Create Host
    Host host = new Host(
      0, // Host ID
      new RamProvisionerSimple(ram),
      new BwProvisionerSimple(bw),
      storage,
      peList, // List of PEs
      new VmSchedulerTimeShared(peList) // Time-Shared Scheduler
    );
    hostList.add(host);

    // Datacenter characteristics
    String arch = "x86";
    String os = "Linux";
    String vmm = "Xen";
    double timezone = 10.0;
    double cost = 3.0;
    double costPerMem = 0.05;
    double costPerStorage = 0.1;
    double costPerBw = 0.0;

    DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
      arch, os, vmm, hostList, timezone, cost, costPerMem,
      costPerStorage, costPerBw
    );

    Datacenter datacenter = null;
    try {
      datacenter = new Datacenter(
        name,
        characteristics,
        new VmAllocationPolicySimple(hostList),
        new ArrayList < Storage > (),
        5.0
      );
    } catch (Exception e) {
      e.printStackTrace();
    }
    return datacenter;
  }
}