package org.cloudbus.cloudsim.edf;

import java.text.DecimalFormat;
import java.util.List;
import org.cloudbus.cloudsim.*;

public class PrintCloudletList {

    public static void printWithMissedDeadlines(List<Cloudlet> list) {
        int missedDeadlineCount = 0;

        System.out.println("\n========== OUTPUT ==========");
        System.out.printf(
            "%-12s%-12s%-16s%-10s%-10s%-15s%-15s%-15s%-15s%n",
            "Cloudlet ID",
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

            System.out.printf(
                "%-12d%-12s%-16d%-10d%-10s%-15s%-15s%-15s%-15s%n",
                cloudlet.getCloudletId(),
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

        System.out.println(
            "\nTotal Missed Deadline Cloudlets: " + missedDeadlineCount
        );
    }
}
