package org.cloudbus.cloudsim.edf;

import java.text.DecimalFormat;
import java.util.List;
import org.cloudbus.cloudsim.*;

public class PrintCloudletList {

    public static void printWithMissedDeadlines(List<Cloudlet> list) {
        int missedDeadlineCount = 0;

        System.out.println("\n");
        // Add Task Length next to Cloudlet ID in the header
        System.out.printf(
            "%-12s%-15s%-12s%-16s%-10s%-10s%-15s%-15s%-15s%-15s%n",
            "Cloudlet ID",
            "Task Length",
            "Status",
            "Data center ID",
            "VM ID",
            "Time",
            "Start Time",
            "Finish Time",
            "Deadline",
            "Missed Deadline"
        );

        DecimalFormat dft = new DecimalFormat("###.##");
        for (Cloudlet cloudlet : list) {
            CloudletWithDeadline cloudletWithDeadline =
                (CloudletWithDeadline) cloudlet;

            String status = cloudlet.getCloudletStatus() == Cloudlet.SUCCESS
                ? "SUCCESS"
                : "FAILED";
            double finishTime = cloudlet.getFinishTime();
            double deadline = cloudletWithDeadline.getDeadline();
            String missedDeadline = finishTime > deadline ? "YES" : "NO";

            if (missedDeadline.equals("YES")) {
                missedDeadlineCount++;
            }

            // Include Task Length in the printout
            System.out.printf(
                "%-12d%-15d%-12s%-16d%-10d%-10s%-15s%-15s%-15s%-15s%n",
                cloudlet.getCloudletId(),
                cloudlet.getCloudletLength(), // Print the cloudlet length
                status,
                cloudlet.getResourceId(),
                cloudlet.getVmId(),
                dft.format(cloudlet.getActualCPUTime()),
                dft.format(cloudlet.getExecStartTime()),
                dft.format(finishTime),
                dft.format(deadline),
                missedDeadline
            );
        }

        System.out.println("\n======= Missed Deadline ========");
        System.out.println(
            "Total Missed Deadline Cloudlets: " + missedDeadlineCount + "\n"
        );
    }

    public static void printResourceUtilization(
        List<Vm> vmList,
        List<Host> hostList,
        int vmMips,
        int ramPerVm,
        long bwPerVm,
        long storagePerVm,
        int simulationTime,
        List<Cloudlet> cloudletList // Add the list of cloudlets
    ) {
        // RAM Utilization - dynamic calculation based on cloudlet length
        long totalRamUsed = 0;

        // Calculate total RAM used by each cloudlet based on its length or execution time
        for (Cloudlet cloudlet : cloudletList) {
            long cloudletRamUsage = cloudlet.getCloudletLength() / 100;
            totalRamUsed += cloudletRamUsage;
        }

        // Total RAM available
        int totalRamAvailable = hostList.size() * 2048; // Assuming 2 GB RAM per host
        double ramUtilization =
            ((double) totalRamUsed / totalRamAvailable) * 100;

        // Bandwidth Utilization
        long totalBwUsed = vmList.size() * bwPerVm;
        long totalBwAvailable = hostList.size() * 10000; // Assuming 10 Gbps per host
        double bwUtilization = ((double) totalBwUsed / totalBwAvailable) * 100;

        // Storage Utilization
        long totalStorageUsed = vmList.size() * storagePerVm;
        long totalStorageAvailable = hostList.size() * 1000000; // Assuming 1 TB storage per host
        double storageUtilization =
            ((double) totalStorageUsed / totalStorageAvailable) * 100;

        // Print Results
        System.out.println("===== Resource Utilization =====");
        System.out.println("RAM Utilization: " + ramUtilization + "%");
        System.out.println("Bandwidth Utilization: " + bwUtilization + "%");
        System.out.println("Storage Utilization: " + storageUtilization + "%");
    }
}
