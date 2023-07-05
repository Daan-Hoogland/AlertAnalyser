import com.kanishka.virustotal.dto.FileScanReport;
import com.kanishka.virustotal.exception.APIKeyNotFoundException;
import com.kanishka.virustotal.exception.QuotaExceededException;
import com.kanishka.virustotal.exception.UnauthorizedAccessException;
import com.kanishka.virustotalv2.VirusTotalConfig;
import com.kanishka.virustotalv2.VirustotalPublicV2;
import com.kanishka.virustotalv2.VirustotalPublicV2Impl;
import models.Event;
import templates.CodeSegment;

import java.io.IOException;

public class VirusTotalFile implements CodeSegment {

    private VirustotalPublicV2 virusTotalRef;

    @Override
    public boolean start(Event event) {
        VirusTotalConfig.getConfigInstance().setVirusTotalAPIKey("");
	try {
            virusTotalRef = new VirustotalPublicV2Impl();
        } catch (APIKeyNotFoundException e) {
            logger.warn("The VirusTotal API key is invalid. VirusTotal calls will not be functional.");
        }

        logger.debug(checkFileReport(event));
        return checkFileReport(event);
    }

    private boolean checkFileReport(Event event) {

        try {
            logger.debug("TODO: replace hash with event hash");
            FileScanReport report = virusTotalRef.getScanReport("275a021bbfb6489e54d471899f7db9d1663fc695ec2fe2a2c4538aabf651fd0f");

            return !(report.getPositives() == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnauthorizedAccessException e) {
            logger.warn("The API the program is trying to reach is private.");
            logger.warn("Update the API key or remove call function from class.");
        } catch (QuotaExceededException e) {
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return checkFileReport(event);
        }
        return true;
    }
}

