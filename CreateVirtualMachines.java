package org.cloudbus.cloudsim.edf;

import java.util.ArrayList;
import java.util.List;
import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.provisioners.*;

public class CreateVirtualMachines {

    public static List<Vm> create(int brokerId) {
        List<Vm> vmList = new ArrayList<>();
        int mips = 1000;
        long size = 10000;
        int ram = 512;
        long bw = 1000;
        int pesNumber = 1;
        String vmm = "Xen";

        for (int i = 0; i < 150; i++) {
            Vm vm = new Vm(
                i,
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
