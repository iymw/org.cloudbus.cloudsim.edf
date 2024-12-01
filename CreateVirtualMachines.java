package org.cloudbus.cloudsim.edf;

import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.provisioners.*;

import java.util.ArrayList;
import java.util.List;

public class CreateVirtualMachines {
  public static List < Vm > create(int brokerId) {
    List < Vm > vmList = new ArrayList < > ();

    // VM configuration
    int mips = 1000;
    long size = 10000;
    int ram = 512;
    long bw = 1000;
    int pesNumber = 1;
    String vmm = "Xen";

    // Create 3 VMs
    for (int i = 0; i < 3; i++) {
      Vm vm = new Vm(
        i, // VM ID
        brokerId,
        mips,
        pesNumber,
        ram,
        bw,
        size,
        vmm,
        new CloudletSchedulerTimeShared()
      );
      vmList.add(vm);
    }

    return vmList;
  }
}