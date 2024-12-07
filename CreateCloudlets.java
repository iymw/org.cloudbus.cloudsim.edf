package org.cloudbus.cloudsim.edf;

import java.util.ArrayList;
import java.util.List;
import org.cloudbus.cloudsim.*;

public class CreateCloudlets {

    public static List<CloudletWithDeadline> create(int brokerId, int vmMips) {
        List<CloudletWithDeadline> cloudletList = new ArrayList<>();

        // Cloudlet parameters
        int numCloudlets = 1000; // Number of cloudlets
        long minLength = 100; // Minimum cloudlet length
        long maxLength = 5000; // Maximum cloudlet length
        long minFileSize = 300; // Minimum file size
        long maxFileSize = 1000; // Maximum file size
        long minOutputSize = 300; // Minimum output size
        long maxOutputSize = 1000; // Maximum output size
        int pesNumber = 1; // Number of PEs required

        // Utilization model
        UtilizationModel utilizationModel = new UtilizationModelFull();

        // Generate cloudlets with deadlines
        for (int i = 0; i < numCloudlets; i++) {
            long length =
                minLength + (long) (Math.random() * (maxLength - minLength)); // Random length
            long fileSize =
                minFileSize +
                (long) (Math.random() * (maxFileSize - minFileSize)); // Random file size
            long outputSize =
                minOutputSize +
                (long) (Math.random() * (maxOutputSize - minOutputSize)); // Random output size

            // Randomly choose between 1.15, 1.25, and 1.50
            double randomFactor = 0;
            double rand = Math.random(); // Generate a random number between 0 and 1

            if (rand < 0.33) {
                randomFactor = 1.15; // 33% chance
            } else if (rand < 0.66) {
                randomFactor = 1.25; // 33% chance
            } else {
                randomFactor = 1.50; // 34% chance
            }

            // Calculate the deadline using the chosen factor
            double executionTimeEstimate = (double) length / vmMips;
            double deadline = executionTimeEstimate * randomFactor;

            // Create cloudlet
            CloudletWithDeadline cloudlet = new CloudletWithDeadline(
                i, // Cloudlet ID
                length,
                pesNumber,
                fileSize,
                outputSize,
                utilizationModel,
                utilizationModel,
                utilizationModel,
                deadline
            );

            cloudlet.setUserId(brokerId); // Set broker ID
            cloudletList.add(cloudlet); // Add cloudlet to list
        }

        return cloudletList;
    }
}
