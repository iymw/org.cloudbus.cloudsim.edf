package org.cloudbus.cloudsim.edf;

import java.util.ArrayList;
import java.util.List;
import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.provisioners.*;

public class CreateDataCenter {

    public static Datacenter create(String name, int numberOfHosts) {
        List<Host> hostList = new ArrayList<>();
        int mips = 1000000;
        int ram = 4096;
        long storage = 1000000;
        int bw = 100000;

        for (int i = 0; i < numberOfHosts; i++) {
            List<Pe> peList = new ArrayList<>();
            peList.add(new Pe(0, new PeProvisionerSimple(mips)));

            Host host = new Host(
                i,
                new RamProvisionerSimple(ram),
                new BwProvisionerSimple(bw),
                storage,
                peList,
                new VmSchedulerTimeShared(peList)
            );
            hostList.add(host);
        }

        DatacenterCharacteristics characteristics =
            new DatacenterCharacteristics(
                "x86",
                "Linux",
                "Xen",
                hostList,
                10.0,
                3.0,
                0.05,
                0.1,
                0.1
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
