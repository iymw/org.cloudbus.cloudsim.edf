package org.cloudbus.cloudsim.edf;

import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CloudsimEDF {
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
            Datacenter datacenter0 = createDatacenter("Datacenter_0");

            // Create Broker
            DatacenterBroker broker = createBroker();
            int brokerId = broker.getId();

            // Create VMs
            List<Vm> vmList = createVirtualMachines(brokerId);

            // Create Cloudlets
            List<Cloudlet> cloudletList = createCloudlets(brokerId);

            // Associate Cloudlets with VMs
            for (int i = 0; i < cloudletList.size(); i++) {
                Cloudlet cloudlet = cloudletList.get(i);
                // Assign cloudlet to the first VM (or you can add more logic to balance load)
                cloudlet.setVmId(vmList.get(i % vmList.size()).getId()); // Assign each cloudlet to a VM in round-robin manner
            }

            // Submit VM and Cloudlet lists to broker
            broker.submitVmList(vmList);
            broker.submitCloudletList(cloudletList);

            // Start the simulation
            CloudSim.startSimulation();

            // Stop the simulation 
            CloudSim.stopSimulation();

            // Final result processing
            List<Cloudlet> newList = broker.getCloudletReceivedList();
            printCloudletList(newList);

        } catch (Exception e) {
            System.out.println("Simulation terminated due to unexpected error:");
            e.printStackTrace();
        }
    }

    /**
     * Creates a datacenter with specific characteristics
     * @param name Datacenter name
     * @return Datacenter instance
     */
    private static Datacenter createDatacenter(String name) {
        // Host list
        List<Host> hostList = new ArrayList<>();

        // PE and Host parameters
        int mips = 1000;
        int ram = 2048; // 2 GB
        long storage = 1000000; // 1 TB
        int bw = 10000;

        // Create Processing Elements (PEs)
        List<Pe> peList = new ArrayList<>();
        Pe pe = new Pe(0, new PeProvisionerSimple(mips));
        peList.add(pe);

        // Create Host
        Host host = new Host(
            0,  // host ID
            new RamProvisionerSimple(ram), 
            new BwProvisionerSimple(bw), 
            storage, 
            peList,  // list of PEs
            new VmSchedulerTimeShared(peList)
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
                new ArrayList<Storage>(), 
                5.0
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datacenter;
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

    /**
     * Creates virtual machines
     * @param brokerId ID of the broker
     * @return List of VMs
     */
    private static List<Vm> createVirtualMachines(int brokerId) {
        List<Vm> vmList = new ArrayList<>();

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
                i,  // VM ID
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

    /**
     * Creates cloudlets for processing
     * @param brokerId ID of the broker
     * @return List of Cloudlets
     */
    private static List<Cloudlet> createCloudlets(int brokerId) {
        List<Cloudlet> cloudletList = new ArrayList<>();

        // Cloudlet parameters
        int numCloudlets = 100;  // 100 cloudlets
        long minLength = 10000;
        long maxLength = 50000;
        long minFileSize = 300;
        long maxFileSize = 1000;
        long minOutputSize = 300;
        long maxOutputSize = 1000;
        int pesNumber = 1;

        // Utilization model
        UtilizationModel utilizationModel = new UtilizationModelFull();

        // Generate 100 cloudlets with random parameters
        for (int i = 0; i < numCloudlets; i++) {
            long length = minLength + (long)(Math.random() * (maxLength - minLength));  // Random length between minLength and maxLength
            long fileSize = minFileSize + (long)(Math.random() * (maxFileSize - minFileSize));  // Random file size between minFileSize and maxFileSize
            long outputSize = minOutputSize + (long)(Math.random() * (maxOutputSize - minOutputSize));  // Random output size between minOutputSize and maxOutputSize

            Cloudlet cloudlet = new Cloudlet(
                i,  // Cloudlet ID
                length,  // Cloudlet length
                pesNumber, 
                fileSize, 
                outputSize, 
                utilizationModel, 
                utilizationModel, 
                utilizationModel
            );
            
            cloudlet.setUserId(brokerId);
            cloudletList.add(cloudlet);
        }

        return cloudletList;
    }

    /**
     * Prints details of processed cloudlets
     * @param list List of cloudlets
     */
    private static void printCloudletList(List<Cloudlet> list) {
        int size = list.size();
        Cloudlet cloudlet;

        System.out.println("\n========== OUTPUT ==========");
        System.out.println("Cloudlet ID\tStatus\tData center ID\tVM ID\tTime\tStart Time\tFinish Time");

        DecimalFormat dft = new DecimalFormat("###.##");
        for (int i = 0; i < size; i++) {
            cloudlet = list.get(i);
            
            System.out.print(cloudlet.getCloudletId() + "\t\t");

            if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
                System.out.print("SUCCESS\t");
                System.out.print(cloudlet.getResourceId() + "\t\t");
                System.out.print(cloudlet.getVmId() + "\t");
                System.out.print(dft.format(cloudlet.getActualCPUTime()) + "\t");
                System.out.print(dft.format(cloudlet.getExecStartTime()) + "\t\t");
                System.out.print(dft.format(cloudlet.getFinishTime()) + "\t");
            } else {
                System.out.print("FAILED\t");
            }
            System.out.println();
        }
    }
}
