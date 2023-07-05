import com.fasterxml.jackson.annotation.JsonProperty;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import models.Event;
import utils.GenericSerializer;
import templates.CodeSegment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class AbuseIPDB implements CodeSegment {
    @Override
    public boolean start(Event event) {
        try {
            HttpResponse<JsonNode> response = Unirest.get("https://www.abuseipdb.com/check/{ip}/json?key={token}&days={days}")
                    .routeParam("ip", event.getSrcIp())
                    .routeParam("token", "")
                    .routeParam("days", "20").asJson();

            GenericSerializer<AbuseIPDBReply> genericSerializer = new GenericSerializer<>(AbuseIPDBReply.class);

            ArrayList<AbuseIPDBReply> replyList = new ArrayList<>();
            for (int i = 0; i < response.getBody().getArray().length(); i++) {
                try {

                    replyList.add(genericSerializer.serializeObject(response.getBody().getArray().get(i).toString()));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (replyList.size() > 0) {
                replyList.forEach(reply -> logger.debug(reply.toString()));
            } else {
                logger.debug("Empty replylist.");
            }

        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static class AbuseIPDBReply {
        @JsonProperty("ip")
        private String ip;

        @JsonProperty("country")
        private String country;

        @JsonProperty("isoCode")
        private String isoCode;

        @JsonProperty("category")
        private int[] category;

        @JsonProperty("created")
        private String created;

        public AbuseIPDBReply() {
        }

        public AbuseIPDBReply(String ip, String country, String isoCode, int[] category, String created) {
            this.ip = ip;
            this.country = country;
            this.isoCode = isoCode;
            this.category = category;
            this.created = created;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getIsoCode() {
            return isoCode;
        }

        public void setIsoCode(String isoCode) {
            this.isoCode = isoCode;
        }

        public int[] getCategory() {
            return category;
        }

        public void setCategory(int[] category) {
            this.category = category;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        @Override
        public String toString() {
            return "AbuseIPDBReply{" +
                    "ip='" + ip + '\'' +
                    ", country='" + country + '\'' +
                    ", isoCode='" + isoCode + '\'' +
                    ", category=" + Arrays.toString(category) +
                    ", created='" + created + '\'' +
                    '}';
        }
    }
}

