package org.cloudbus.cloudsim.edf;

import java.util.Calendar;
import java.util.List;
import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;

public class Main {

    public static void main(String[] args) {
        try {
            // Number of cloud users
            int num_user = 1;

            // Calendar instance
            Calendar calendar = Calendar.getInstance();

            // Trace flag (set to true for detailed tracing)
            boolean trace_flag = false;

            // Initialize CloudSim
            CloudSim.init(num_user, calendar, trace_flag);

            // Create Datacenter
            Datacenter datacenter0 = CreateDataCenter.create(
                "Datacenter_0",
                200
            );

            // Create Broker
            DatacenterBroker broker = createBroker();
            int brokerId = broker.getId();

            // VM MIPS for deadline calculation (ensure consistency with CreateVirtualMachines)
            int vmMips = 1000;

            // Create VMs
            List<Vm> vmList = CreateVirtualMachines.create(brokerId);

            // Create Cloudlets with deadlines
            List<CloudletWithDeadline> cloudletList = CreateCloudlets.create(
                brokerId,
                vmMips
            );

            // Associate Cloudlets with VMs
            for (int i = 0; i < cloudletList.size(); i++) {
                CloudletWithDeadline cloudlet = cloudletList.get(i);
                // Assign cloudlet to a VM in a round-robin manner
                cloudlet.setVmId(vmList.get(i % vmList.size()).getId());
            }

            // Submit VM and Cloudlet lists to broker
            broker.submitVmList(vmList);
            broker.submitCloudletList(cloudletList);

            // Start the simulation
            CloudSim.startSimulation();

            // Stop the simulation
            CloudSim.stopSimulation();

            // Retrieve the actual simulation time
            int simulationTime = (int) CloudSim.clock(); // Dynamically retrieve the simulation clock time

            // Final result processing
            List<Cloudlet> newList = broker.getCloudletReceivedList();

            // Print Cloudlet list and calculate missed deadlines
            PrintCloudletList.printWithMissedDeadlines(newList);

            // Call printResourceUtilization with the dynamic simulation time
            PrintCloudletList.printResourceUtilization(
                vmList,
                datacenter0.getHostList(),
                vmMips,
                512,
                1000,
                10000,
                simulationTime,
                newList // Pass cloudlet list here
            );
        } catch (Exception e) {
            System.out.println(
                "Simulation terminated due to unexpected error:"
            );
            e.printStackTrace();
        }
    }

    /**
     * Creates a broker
     * @return DatacenterBroker instance
     */
    private static DatacenterBroker createBroker() {
        DatacenterBroker broker = null;
        try {
            broker = new DatacenterBroker("Broker");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return broker;
    }
}
