package org.cloudbus.cloudsim.edf;

import java.util.ArrayList;
import java.util.List;
import org.cloudbus.cloudsim.*;

public class CreateCloudlets {

    public static List<CloudletWithDeadline> create(int brokerId, int vmMips) {
        List<CloudletWithDeadline> cloudletList = new ArrayList<>();

        // Cloudlet parameters
        int numCloudlets = 200; // Number of cloudlets
        long minLength = 10000; // Minimum cloudlet length
        long maxLength = 50000; // Maximum cloudlet length
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

            // Calculate execution time estimate and randomized deadline
            double executionTimeEstimate = (double) length / vmMips;
            double randomFactor = 1.50 + (Math.random() * 1.15); // Random multiplier between 1.5 and 2.5
            double deadline = executionTimeEstimate * randomFactor; // Apply random multiplier

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
