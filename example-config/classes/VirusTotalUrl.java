
import com.kanishka.virustotal.dto.FileScanReport;
import com.kanishka.virustotal.dto.ScanInfo;
import com.kanishka.virustotal.exception.APIKeyNotFoundException;
import com.kanishka.virustotal.exception.InvalidArguentsException;
import com.kanishka.virustotal.exception.QuotaExceededException;
import com.kanishka.virustotal.exception.UnauthorizedAccessException;
import com.kanishka.virustotalv2.VirusTotalConfig;
import com.kanishka.virustotalv2.VirustotalPublicV2;
import com.kanishka.virustotalv2.VirustotalPublicV2Impl;
import models.Event;
import templates.CodeSegment;

import java.io.IOException;

public class VirusTotalUrl implements CodeSegment {

    private VirustotalPublicV2 virusTotalRef;

    @Override
    public boolean start(Event event) {
        
        VirusTotalConfig.getConfigInstance().setVirusTotalAPIKey("");
	    
        try {
            virusTotalRef = new VirustotalPublicV2Impl();
        } catch (APIKeyNotFoundException e) {
            logger.warn("The VirusTotal API key is invalid. VirusTotal calls will not be functional.");
        }

        return getReport(event);
    }

    private boolean scanUrl(Event event) {
        try {
            ScanInfo scanInfo[] = virusTotalRef.scanUrls(new String[]{event.getHttp().getHostname() + event.getHttp().getUrl()});

            // Take 10 seconds to allow VirusTotal to scan the item.
            Thread.sleep(10000);
            FileScanReport[] reports = virusTotalRef.getUrlScanReport(new String[]{event.getHttp().getHostname() + event.getHttp().getUrl()}, false);

            switch (reports[0].getResponseCode()) {
                case 1:
                    logger.trace("case 1");
                    return !(reports[0].getPositives() == 0);
                case 0:
                    logger.trace("case 0");
                    logger.trace("Nothing found, call this method again (recursion)");
                    return scanUrl(event);
                case -2:
                    logger.trace("case -2");
                    logger.trace("Try again, call method to getReport and return that value");
                    Thread.sleep(5000);
                    return getReport(event);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidArguentsException e) {
            e.printStackTrace();
        } catch (UnauthorizedAccessException e) {
            logger.warn("The API the program is trying to reach is private.");
            logger.warn("Update the API key or remove call function from class.");
        } catch (QuotaExceededException e) {
            try {
                Thread.sleep(20000);
                return scanUrl(event);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            return scanUrl(event);
        }

        // If everything fails, just return true to not make a rule.
        return true;
    }

    private boolean getReport(Event event) {
        try {
            FileScanReport[] reports = virusTotalRef.getUrlScanReport(new String[]{event.getHttp().getHostname() + event.getHttp().getUrl()}, false);

            switch (reports[0].getResponseCode()) {
                case 1:
                    logger.trace("case 1");
                    return !(reports[0].getPositives() == 0);
                case 0:
                    logger.trace("case 0");
                    logger.trace("Nothing found, first scan then try again.");
                    return scanUrl(event);
                case -2:
                    logger.trace("case -2");
                    logger.trace("Try again");
                    Thread.sleep(5000);
                    return getReport(event);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidArguentsException e) {
            e.printStackTrace();
        } catch (UnauthorizedAccessException e) {
            logger.warn("The API the program is trying to reach is private.");
        } catch (QuotaExceededException e) {
            logger.debug("Quota exceeded", e);
            try {
                Thread.sleep(20000);
                return getReport(event);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            logger.trace("Wait x seconds and try again.");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // If everything fails, just return true to not make a rule.
        return true;
    }
}

