package com.unisoft.collection.settings.branch.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BranchAPIService {

    @Value("${ific.branch.api.url}")
    private String branchApiUrl;

    public BranchAPIResponse getBranchInfo() {

        BranchAPIResponse branchAPIResponse = new BranchAPIResponse();
        try {
            Gson gson = new Gson();
            HttpClient httpClient = HttpClientBuilder.create().build();
            //HttpPost post = new HttpPost(branchApiUrl);
            //post.setEntity(new UrlEncodedFormEntity(employeeApiPayload));
            //post.addHeader("Content-type", "application/json");
            //post.addHeader("Accept", "application/json");
            //StringEntity postingString = new StringEntity(gson.toJson(employeeApiPayload), ContentType.APPLICATION_JSON);

            //post.setEntity(postingString);
            //HttpResponse response = httpClient.execute(post);
            //String jsonString = EntityUtils.toString(response.getEntity());
            String jsonString = "{\n" +
                    "  \"Branch\": {\n" +
                    "    \"1\": {\n" +
                    "      \"CODE\": \"0001\",\n" +
                    "      \"MNEMONIC\": \"C001\",\n" +
                    "      \"NAME\": \"Sorail Upzilla Uposhakha \",\n" +
                    "      \"ROUTING\": \"120120431\",\n" +
                    "      \"CABAD1\": \"Sorail Tower, P.O -Sorail, \",\n" +
                    "      \"CABAD2\": \"P.S-Sorail Dist. - Brahmanbaria \",\n" +
                    "      \"CABAD3\": \" \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"2\": {\n" +
                    "      \"CODE\": \"0002\",\n" +
                    "      \"MNEMONIC\": \"B685\",\n" +
                    "      \"NAME\": \"Kawtali Uposhakha, Brahmanbaria \",\n" +
                    "      \"ROUTING\": \"120120431\",\n" +
                    "      \"CABAD1\": \"Abdul Hai Mansion, Holding No- 1329\",\n" +
                    "      \"CABAD2\": \", Kawtoli Main Road, Ward No- 10, B\",\n" +
                    "      \"CABAD3\": \"rahmanbaria Pourashava, Thana- Brah\",\n" +
                    "      \"CABAD4\": \"manbaria Sadar, District- Brahmanba\",\n" +
                    "      \"CABAD5\": \"ria \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"3\": {\n" +
                    "      \"CODE\": \"0003\",\n" +
                    "      \"MNEMONIC\": \"B686\",\n" +
                    "      \"NAME\": \"Sarak Bazar Uposhakha,Brahmanbaria \",\n" +
                    "      \"ROUTING\": \"120120431\",\n" +
                    "      \"CABAD1\": \"Sarak Bazar Road, Ward No- 04, Pour\",\n" +
                    "      \"CABAD2\": \"ashava- Brahmanbaria Upazilla/Thana\",\n" +
                    "      \"CABAD3\": \"- Brahmanbaria Sadar, District- Bra\",\n" +
                    "      \"CABAD4\": \"hmanbaria \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"4\": {\n" +
                    "      \"CODE\": \"0004\",\n" +
                    "      \"MNEMONIC\": \"B687\",\n" +
                    "      \"NAME\": \"Nandanpur Uposhakha,Brahmanbaria \",\n" +
                    "      \"ROUTING\": \"120120431\",\n" +
                    "      \"CABAD1\": \"Bhai Bhai Market, Village- Nandanpu\",\n" +
                    "      \"CABAD2\": \"r Bazar, Union- 2 no Budhol, Thana-\",\n" +
                    "      \"CABAD3\": \" Brahmanbaria Sadar, District- Brah\",\n" +
                    "      \"CABAD4\": \"manbaria. \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"5\": {\n" +
                    "      \"CODE\": \"0005\",\n" +
                    "      \"MNEMONIC\": \"B688\",\n" +
                    "      \"NAME\": \"Nasirnagar Uposhakha,Brahmanbaria \",\n" +
                    "      \"ROUTING\": \"120120431\",\n" +
                    "      \"CABAD1\": \"Village-Nasirnagar, \",\n" +
                    "      \"CABAD2\": \"Union-Nasirnagar, \",\n" +
                    "      \"CABAD3\": \"Thana-Nasirnagar, \",\n" +
                    "      \"CABAD4\": \"District-Brahmanbaria. \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"6\": {\n" +
                    "      \"CODE\": \"0006\",\n" +
                    "      \"MNEMONIC\": \"B689\",\n" +
                    "      \"NAME\": \"Nobinagar Uposhakha,Brahmanbaria \",\n" +
                    "      \"ROUTING\": \"120120431\",\n" +
                    "      \"CABAD1\": \"Haji Akhtaruzzaman Super Maket, Hol\",\n" +
                    "      \"CABAD2\": \"ding No.-00796, Court Road, Ward No\",\n" +
                    "      \"CABAD3\": \".-04, Nobinagar Pourashava, Thana-N\",\n" +
                    "      \"CABAD4\": \"obinagar, District-Brahmanbaria \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"7\": {\n" +
                    "      \"CODE\": \"0007\",\n" +
                    "      \"MNEMONIC\": \"B690\",\n" +
                    "      \"NAME\": \"Biswaroad Point,Brahmanbaria \",\n" +
                    "      \"ROUTING\": \"120120431\",\n" +
                    "      \"CABAD1\": \"Haji Naosha Plaza, Dhaka-Sylhet Hig\",\n" +
                    "      \"CABAD2\": \"hway, Ward No-06, Brahmanbaria Pour\",\n" +
                    "      \"CABAD3\": \"ashava, Thana-Brahmanbaria, Distric\",\n" +
                    "      \"CABAD4\": \"t-Brahmanbaria \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"8\": {\n" +
                    "      \"CODE\": \"0008\",\n" +
                    "      \"MNEMONIC\": \"B691\",\n" +
                    "      \"NAME\": \"Fulbaria-Brahmanbaria Uposhakha \",\n" +
                    "      \"ROUTING\": \"120120431\",\n" +
                    "      \"CABAD1\": \"A. Khalek Complex, Holding No.- 997\",\n" +
                    "      \"CABAD2\": \", Cumilla-Sylhet Highway Road, Ward\",\n" +
                    "      \"CABAD3\": \" No.- 03, Brahmanbaria Pourashava, \",\n" +
                    "      \"CABAD4\": \"Thana- Brahmanbaria Sadar, District\",\n" +
                    "      \"CABAD5\": \"- Brahmanbaria \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"9\": {\n" +
                    "      \"CODE\": \"0009\",\n" +
                    "      \"MNEMONIC\": \"B692\",\n" +
                    "      \"NAME\": \"Krishnanagar Bazar Uposhakha,Bbaria\",\n" +
                    "      \"ROUTING\": \"120120431\",\n" +
                    "      \"CABAD1\": \"Amin Plaza, Village-Krishnanagar Ba\",\n" +
                    "      \"CABAD2\": \"zar, Union-Krishnanagar, Thana-Nobi\",\n" +
                    "      \"CABAD3\": \"nagar, District-Brahmanbaria \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"10\": {\n" +
                    "      \"CODE\": \"0010\",\n" +
                    "      \"MNEMONIC\": \"B693\",\n" +
                    "      \"NAME\": \"Islampur Bazar Upo.,Brahmanbaria \",\n" +
                    "      \"ROUTING\": \"120120431\",\n" +
                    "      \"CABAD1\": \"MiahRa Mansion, Village-Islampur Ba\",\n" +
                    "      \"CABAD2\": \"zar, Union-Budhonti, Thana-Bijoynag\",\n" +
                    "      \"CABAD3\": \"ar, District-Brahmanbaria \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"11\": {\n" +
                    "      \"CODE\": \"0011\",\n" +
                    "      \"MNEMONIC\": \"B694\",\n" +
                    "      \"NAME\": \"Gas Field Gate ,Brahmanbaria \",\n" +
                    "      \"ROUTING\": \"120120431\",\n" +
                    "      \"CABAD1\": \"Asma Nir, Holding No-758/3, Highway\",\n" +
                    "      \"CABAD2\": \" Road, Ward No-01, Pourashova-Brahm\",\n" +
                    "      \"CABAD3\": \"anbaria, Thana-Brahmanbaria Sadar, \",\n" +
                    "      \"CABAD4\": \"District-Brahmanbaria \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"12\": {\n" +
                    "      \"CODE\": \"0012\",\n" +
                    "      \"MNEMONIC\": \"B695\",\n" +
                    "      \"NAME\": \"Bijoynagar Amtoli Bazar \",\n" +
                    "      \"ROUTING\": \"120120431\",\n" +
                    "      \"CABAD1\": \"Jahangir Market, Area- Amtoli \",\n" +
                    "      \"CABAD2\": \"Bazar, Union- 2 no. Chandura, \",\n" +
                    "      \"CABAD3\": \"Thana- Bijoynagar, District- \",\n" +
                    "      \"CABAD4\": \"Brahmanbaria \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"13\": {\n" +
                    "      \"CODE\": \"0020\",\n" +
                    "      \"MNEMONIC\": \"B703\",\n" +
                    "      \"NAME\": \"Ramu Uposhakha,Coxs Bazar \",\n" +
                    "      \"ROUTING\": \"120220252\",\n" +
                    "      \"CABAD1\": \"Chairman Paradise, Village-Ramu \",\n" +
                    "      \"CABAD2\": \"Upazilla Parishad Gate, \",\n" +
                    "      \"CABAD3\": \"Union-Fotehkhanrkul, Thana-Ramu, \",\n" +
                    "      \"CABAD4\": \"District-Coxs Bazar \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"14\": {\n" +
                    "      \"CODE\": \"0021\",\n" +
                    "      \"MNEMONIC\": \"B704\",\n" +
                    "      \"NAME\": \"Court Bazar Uposhakha,Cox's Bazar \",\n" +
                    "      \"ROUTING\": \"120220252\",\n" +
                    "      \"CABAD1\": \"Fazal Market, Village/Area-Court Ba\",\n" +
                    "      \"CABAD2\": \"zar, Union-Rotna Palong, Thana-Ukhi\",\n" +
                    "      \"CABAD3\": \"ya, District-Coxs Bazar \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"15\": {\n" +
                    "      \"CODE\": \"0022\",\n" +
                    "      \"MNEMONIC\": \"B705\",\n" +
                    "      \"NAME\": \"Eidgah Bazar Uposhakha,Coxs Bazar \",\n" +
                    "      \"ROUTING\": \"120220252\",\n" +
                    "      \"CABAD1\": \"Area-Eidgah Bazar, Union-Jalalabad,\",\n" +
                    "      \"CABAD2\": \" Thana-Eidgah, District-Cox's Bazar\",\n" +
                    "      \"CABAD3\": \" \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"16\": {\n" +
                    "      \"CODE\": \"0023\",\n" +
                    "      \"MNEMONIC\": \"B706\",\n" +
                    "      \"NAME\": \"Sonar Para Uposhakha,Cox's Bazar \",\n" +
                    "      \"ROUTING\": \"120220252\",\n" +
                    "      \"CABAD1\": \"Amin Tower, Area-Sonar Para, Union-\",\n" +
                    "      \"CABAD2\": \"Jaliapalong, Thana-Ukhiya, District\",\n" +
                    "      \"CABAD3\": \"-Cox's Bazar \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"17\": {\n" +
                    "      \"CODE\": \"0024\",\n" +
                    "      \"MNEMONIC\": \"B707\",\n" +
                    "      \"NAME\": \"Teknaf Uposhakha,Cox's Bazar \",\n" +
                    "      \"ROUTING\": \"120220252\",\n" +
                    "      \"CABAD1\": \"Jalal Ahmed Market, Holding No-P.D.\",\n" +
                    "      \"CABAD2\": \"-162, Road Name-Kapla Chattar, Ward\",\n" +
                    "      \"CABAD3\": \" No-05, Pauroshova-Teknaf, Thana-Te\",\n" +
                    "      \"CABAD4\": \"knaf, District-Cox's Bazar \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"18\": {\n" +
                    "      \"CODE\": \"0025\",\n" +
                    "      \"MNEMONIC\": \"B708\",\n" +
                    "      \"NAME\": \"Link Road-Cox's Bazar Uposhakha \",\n" +
                    "      \"ROUTING\": \"120220252\",\n" +
                    "      \"CABAD1\": \"Arif Market-2, Area-Link Road, Unio\",\n" +
                    "      \"CABAD2\": \"n-Jhilongza, Thana-Cox's Bazar Sada\",\n" +
                    "      \"CABAD3\": \"r, District-Cox's Bazar \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"19\": {\n" +
                    "      \"CODE\": \"0026\",\n" +
                    "      \"MNEMONIC\": \"B709\",\n" +
                    "      \"NAME\": \"Naikhongchari Uposhakha,Bandarban \",\n" +
                    "      \"ROUTING\": \"120220252\",\n" +
                    "      \"CABAD1\": \"Kabir Tower, Holding No-R-307, Vill\",\n" +
                    "      \"CABAD2\": \"age-Naikhongchari, Union-Naikhongch\",\n" +
                    "      \"CABAD3\": \"ari, Thana-Naikhongchari, District-\",\n" +
                    "      \"CABAD4\": \"Bandarban \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"20\": {\n" +
                    "      \"CODE\": \"0027\",\n" +
                    "      \"MNEMONIC\": \"B710\",\n" +
                    "      \"NAME\": \"Hnila Bazar Uposhakha,Coxs Bazar \",\n" +
                    "      \"ROUTING\": \"120220252\",\n" +
                    "      \"CABAD1\": \"Hazi Md. Rashid Tower, Village- \",\n" +
                    "      \"CABAD2\": \"Hnila Bazar, Union- Hnila, Thana- \",\n" +
                    "      \"CABAD3\": \"Teknaf, District- Cox's Bazar \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"21\": {\n" +
                    "      \"CODE\": \"0028\",\n" +
                    "      \"MNEMONIC\": \"B711\",\n" +
                    "      \"NAME\": \"Shamlapur Bazar Upo,Coxs Bazar \",\n" +
                    "      \"ROUTING\": \"120220252\",\n" +
                    "      \"CABAD1\": \"I. K. Tower-2, Village- Shamlapur \",\n" +
                    "      \"CABAD2\": \"Bazar, Union- 05 No. Baharchora, \",\n" +
                    "      \"CABAD3\": \"Thana- Teknaf, District- Cox's \",\n" +
                    "      \"CABAD4\": \"Bazar \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"22\": {\n" +
                    "      \"CODE\": \"0029\",\n" +
                    "      \"MNEMONIC\": \"B712\",\n" +
                    "      \"NAME\": \"Moricha Uposhakha,Coxs Bazar \",\n" +
                    "      \"ROUTING\": \"120220252\",\n" +
                    "      \"CABAD1\": \"Shadhin Tower, Area- Moricha Bazar \",\n" +
                    "      \"CABAD2\": \"Uttar Station, Thana- Ukhiya, \",\n" +
                    "      \"CABAD3\": \"District- Cox's Bazar \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"23\": {\n" +
                    "      \"CODE\": \"0030\",\n" +
                    "      \"MNEMONIC\": \"B713\",\n" +
                    "      \"NAME\": \"Oxygen MoorUposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120157275\",\n" +
                    "      \"CABAD1\": \"Jahan Center, Holding No.-3669/C, B\",\n" +
                    "      \"CABAD2\": \"angabandhu Avenue, Ward No.-03, Pan\",\n" +
                    "      \"CABAD3\": \"chlaish, Chattogram City Corporatio\",\n" +
                    "      \"CABAD4\": \"n, Thana-Baizid Bostami, District-C\",\n" +
                    "      \"CABAD5\": \"hattogram \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"24\": {\n" +
                    "      \"CODE\": \"0031\",\n" +
                    "      \"MNEMONIC\": \"B714\",\n" +
                    "      \"NAME\": \"Kaptai Rastar Matha Uposhakha \",\n" +
                    "      \"ROUTING\": \"120157275\",\n" +
                    "      \"CABAD1\": \"Ezahar Chowdhury Centre, Holding No\",\n" +
                    "      \"CABAD2\": \"- 3654, Kaptai Rastar Matha, Ward N\",\n" +
                    "      \"CABAD3\": \"o- 5, Chattogram City Corporation, \",\n" +
                    "      \"CABAD4\": \"Thana- Chandgaon, District- Chattog\",\n" +
                    "      \"CABAD5\": \"ram. \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"25\": {\n" +
                    "      \"CODE\": \"0032\",\n" +
                    "      \"MNEMONIC\": \"B715\",\n" +
                    "      \"NAME\": \"Sharafvata Uposhakha,Chattagram \",\n" +
                    "      \"ROUTING\": \"120157275\",\n" +
                    "      \"CABAD1\": \"Hamid Sharif Market, Village-Sharaf\",\n" +
                    "      \"CABAD2\": \"vata, Union-8 No. Sharafvata, Thana\",\n" +
                    "      \"CABAD3\": \"-Rangunia, Dist-Chattagram \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"26\": {\n" +
                    "      \"CODE\": \"0033\",\n" +
                    "      \"MNEMONIC\": \"B716\",\n" +
                    "      \"NAME\": \"Marium Nagar Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120157275\",\n" +
                    "      \"CABAD1\": \"Hazrat Shah Mujibullah (R.A.) Marke\",\n" +
                    "      \"CABAD2\": \"t, Choumuhani, Village-Marium Nagar\",\n" +
                    "      \"CABAD3\": \", Union-Marium Nagar, Thana-Ranguni\",\n" +
                    "      \"CABAD4\": \"a, District-Chattogram \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"27\": {\n" +
                    "      \"CODE\": \"0034\",\n" +
                    "      \"MNEMONIC\": \"B717\",\n" +
                    "      \"NAME\": \"Madunaghat Uposhakha, Chattogram. \",\n" +
                    "      \"ROUTING\": \"120157275\",\n" +
                    "      \"CABAD1\": \"Village-Madunaghat, Union-13 No. So\",\n" +
                    "      \"CABAD2\": \"uth Madarshah, Thana-Hathazari, Dis\",\n" +
                    "      \"CABAD3\": \"trict- Chattogram. \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"28\": {\n" +
                    "      \"CODE\": \"0035\",\n" +
                    "      \"MNEMONIC\": \"B718\",\n" +
                    "      \"NAME\": \"Dovashi Bazar Uposhakha \",\n" +
                    "      \"ROUTING\": \"120157275\",\n" +
                    "      \"CABAD1\": \"Sheba Pathology, Village-Dovashi \",\n" +
                    "      \"CABAD2\": \"Bazar, Union-11 No Chandraghona \",\n" +
                    "      \"CABAD3\": \"Kadamtoli, Thana-Rangunia, \",\n" +
                    "      \"CABAD4\": \"District-Chattogram \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"29\": {\n" +
                    "      \"CODE\": \"0036\",\n" +
                    "      \"MNEMONIC\": \"B719\",\n" +
                    "      \"NAME\": \"Rangunia Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120157275\",\n" +
                    "      \"CABAD1\": \"BCCUL Somobai Market, \",\n" +
                    "      \"CABAD2\": \"Chottagram-Kaptai Highway, Ward \",\n" +
                    "      \"CABAD3\": \"No-08, Rangunia Pauroshova, \",\n" +
                    "      \"CABAD4\": \"Thana-Rangunia, District-Chattogram\",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"30\": {\n" +
                    "      \"CODE\": \"0040\",\n" +
                    "      \"MNEMONIC\": \"B723\",\n" +
                    "      \"NAME\": \"Sarkerhat Uposhakha, Chattogram \",\n" +
                    "      \"ROUTING\": \"120153224\",\n" +
                    "      \"CABAD1\": \"Hasan Center Sarkerhat, Haji Safar \",\n" +
                    "      \"CABAD2\": \"Ali Sarkar Road (Station Road), War\",\n" +
                    "      \"CABAD3\": \"d No- 01, Chattogram City Corporati\",\n" +
                    "      \"CABAD4\": \"on, Thana- Hathazari, District- Cha\",\n" +
                    "      \"CABAD5\": \"ttogram \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"31\": {\n" +
                    "      \"CODE\": \"0041\",\n" +
                    "      \"MNEMONIC\": \"B724\",\n" +
                    "      \"NAME\": \"Aman Bazar Uposhakha, Chattogram \",\n" +
                    "      \"ROUTING\": \"120153224\",\n" +
                    "      \"CABAD1\": \"N S Tower, Village- Chikon Dandi, M\",\n" +
                    "      \"CABAD2\": \"ouza No.- Chikon Dandi, Union- Chik\",\n" +
                    "      \"CABAD3\": \"on Dandi, District- Chattogram \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"32\": {\n" +
                    "      \"CODE\": \"0042\",\n" +
                    "      \"MNEMONIC\": \"B725\",\n" +
                    "      \"NAME\": \"Raozan Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120153224\",\n" +
                    "      \"CABAD1\": \"Alam Plaza, Holding No.-785, Pouras\",\n" +
                    "      \"CABAD2\": \"hava Road, Ward No. 08, Pourashava-\",\n" +
                    "      \"CABAD3\": \"Raozan, Thana-Raozan, District-Chat\",\n" +
                    "      \"CABAD4\": \"togram \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"33\": {\n" +
                    "      \"CODE\": \"0043\",\n" +
                    "      \"MNEMONIC\": \"B726\",\n" +
                    "      \"NAME\": \"Gohira Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120153224\",\n" +
                    "      \"CABAD1\": \"Rangamati Road, Ward No-03, Pourash\",\n" +
                    "      \"CABAD2\": \"ava-Raozan, Thana-Raozan, District-\",\n" +
                    "      \"CABAD3\": \"Chattogram \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"34\": {\n" +
                    "      \"CODE\": \"0044\",\n" +
                    "      \"MNEMONIC\": \"B727\",\n" +
                    "      \"NAME\": \"Madanhat Uposhakha,Chattrogram \",\n" +
                    "      \"ROUTING\": \"120153224\",\n" +
                    "      \"CABAD1\": \"Abdul Haque Market, Village- Madanh\",\n" +
                    "      \"CABAD2\": \"at, Upozilla- Fathepur, Thana- Fath\",\n" +
                    "      \"CABAD3\": \"epur, District- Chattrogram \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"35\": {\n" +
                    "      \"CODE\": \"0045\",\n" +
                    "      \"MNEMONIC\": \"B728\",\n" +
                    "      \"NAME\": \"Katirhat Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120153224\",\n" +
                    "      \"CABAD1\": \"Md.Hossain Etim Market, Village- We\",\n" +
                    "      \"CABAD2\": \"st Dholoi Katirhat, Union- Dholoi, \",\n" +
                    "      \"CABAD3\": \"Thana- Hathazari, District- Chattog\",\n" +
                    "      \"CABAD4\": \"ram \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"36\": {\n" +
                    "      \"CODE\": \"0046\",\n" +
                    "      \"MNEMONIC\": \"B729\",\n" +
                    "      \"NAME\": \"Chowdhuryhat Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120153224\",\n" +
                    "      \"CABAD1\": \"Yes Tower, Village-Chowdhuryhat, Un\",\n" +
                    "      \"CABAD2\": \"ion-Chikondandi, Thana-Hathazari, D\",\n" +
                    "      \"CABAD3\": \"istrict- Chattogram \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"37\": {\n" +
                    "      \"CODE\": \"0055\",\n" +
                    "      \"MNEMONIC\": \"B738\",\n" +
                    "      \"NAME\": \"Temohoni Bazar Uposhakha,Chattogram\",\n" +
                    "      \"ROUTING\": \"120152746\",\n" +
                    "      \"CABAD1\": \"Solaiman Company Market, \",\n" +
                    "      \"CABAD2\": \"Village/Area- Kanchannagar, Union- \",\n" +
                    "      \"CABAD3\": \"Kanchannagar, Upozila/Thana- \",\n" +
                    "      \"CABAD4\": \"Fatikchari, District- Chattogram \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"38\": {\n" +
                    "      \"CODE\": \"0056\",\n" +
                    "      \"MNEMONIC\": \"B739\",\n" +
                    "      \"NAME\": \"Nazirhat Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120152746\",\n" +
                    "      \"CABAD1\": \"Gulshan Plaza, Khagrachari Road, Wa\",\n" +
                    "      \"CABAD2\": \"rd No-08, Pourashava-Nazirhat, Than\",\n" +
                    "      \"CABAD3\": \"a-Fatikchari, District-Chattogram \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"39\": {\n" +
                    "      \"CODE\": \"0057\",\n" +
                    "      \"MNEMONIC\": \"B740\",\n" +
                    "      \"NAME\": \"Boidyer Hat Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120152746\",\n" +
                    "      \"CABAD1\": \"Mahbub Plaza, Village-Boidyer Hat B\",\n" +
                    "      \"CABAD2\": \"azar, Union-11 No. Suabil, Thana-Bh\",\n" +
                    "      \"CABAD3\": \"ujpur, District-Chattogram \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"40\": {\n" +
                    "      \"CODE\": \"0058\",\n" +
                    "      \"MNEMONIC\": \"B741\",\n" +
                    "      \"NAME\": \"Noajishpur Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120152746\",\n" +
                    "      \"CABAD1\": \"Sikder Plaza, Village-Noajishpur, U\",\n" +
                    "      \"CABAD2\": \"nion-15 No. Noajishpur, Thana-Raoza\",\n" +
                    "      \"CABAD3\": \"n, District-Chattogram \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"41\": {\n" +
                    "      \"CODE\": \"0059\",\n" +
                    "      \"MNEMONIC\": \"B742\",\n" +
                    "      \"NAME\": \"Kazirhat Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120152746\",\n" +
                    "      \"CABAD1\": \"Area-East Buchpur, Union-Buchpur, T\",\n" +
                    "      \"CABAD2\": \"hana-Buchpur, District- Chattogram.\",\n" +
                    "      \"CABAD3\": \" \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"42\": {\n" +
                    "      \"CODE\": \"0060\",\n" +
                    "      \"MNEMONIC\": \"B743\",\n" +
                    "      \"NAME\": \"Narayanhat Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120152746\",\n" +
                    "      \"CABAD1\": \"Haji Mohammad Sarwar Shopping \",\n" +
                    "      \"CABAD2\": \"Center, Village- Narayanhat, Union-\",\n" +
                    "      \"CABAD3\": \"Narayanhat, Thana- Bhujpur, \",\n" +
                    "      \"CABAD4\": \"District- Chattogram \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"43\": {\n" +
                    "      \"CODE\": \"0070\",\n" +
                    "      \"MNEMONIC\": \"B753\",\n" +
                    "      \"NAME\": \"Choto Kumira Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120154694\",\n" +
                    "      \"CABAD1\": \"Santosh Golden Tower, Village-Mosji\",\n" +
                    "      \"CABAD2\": \"dda, Choto Kumira, Union-Kumira, T\",\n" +
                    "      \"CABAD3\": \"hana-Sitakunda, District-Chattogram\",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"44\": {\n" +
                    "      \"CODE\": \"0071\",\n" +
                    "      \"MNEMONIC\": \"B754\",\n" +
                    "      \"NAME\": \"Baro Awlia Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120154694\",\n" +
                    "      \"CABAD1\": \"Kalam Center, Village-Baro Awlia, M\",\n" +
                    "      \"CABAD2\": \"ouza-Middle Sonaichori, Union-Middl\",\n" +
                    "      \"CABAD3\": \"e Sonaichori, Thana-Sitakunda, Dist\",\n" +
                    "      \"CABAD4\": \"rict-Chattogram. \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"45\": {\n" +
                    "      \"CODE\": \"0072\",\n" +
                    "      \"MNEMONIC\": \"B755\",\n" +
                    "      \"NAME\": \"Shiberhat Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120156913\",\n" +
                    "      \"CABAD1\": \"Al-Haj Emlak Hossain Chamber, \",\n" +
                    "      \"CABAD2\": \"Village- Shiberhat, Union- \",\n" +
                    "      \"CABAD3\": \"Sarikait, Thana- Sandwip, District-\",\n" +
                    "      \"CABAD4\": \"Chattogram \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"46\": {\n" +
                    "      \"CODE\": \"0085\",\n" +
                    "      \"MNEMONIC\": \"B768\",\n" +
                    "      \"NAME\": \"Bhairab Bazar Uposhakha,Kishoreganj\",\n" +
                    "      \"ROUTING\": \"120120107\",\n" +
                    "      \"CABAD1\": \"Holding No- 373 (Bhairab Bazar \",\n" +
                    "      \"CABAD2\": \"West), Tin Patti Road, Ward No- 01,\",\n" +
                    "      \"CABAD3\": \"Pourashava- Bhairab, Upozila/Thana-\",\n" +
                    "      \"CABAD4\": \"Bhairab, District- Kishoreganj \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"47\": {\n" +
                    "      \"CODE\": \"0086\",\n" +
                    "      \"MNEMONIC\": \"B769\",\n" +
                    "      \"NAME\": \"Talshahar Uposhakha,Brahmanbaria \",\n" +
                    "      \"ROUTING\": \"120120107\",\n" +
                    "      \"CABAD1\": \"Niaz Market, Village-Talshahar, Uni\",\n" +
                    "      \"CABAD2\": \"on-Talshahar, Thana-Ashuganj, Distr\",\n" +
                    "      \"CABAD3\": \"ict-Brahmanbaria \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"48\": {\n" +
                    "      \"CODE\": \"0087\",\n" +
                    "      \"MNEMONIC\": \"B770\",\n" +
                    "      \"NAME\": \"Kamolpur Uposhakha,Kishoreganj \",\n" +
                    "      \"ROUTING\": \"120120107\",\n" +
                    "      \"CABAD1\": \"Hazi Ful Miah Market, Holding No-12\",\n" +
                    "      \"CABAD2\": \"25, Road Name-Kishoreganj Bhairab R\",\n" +
                    "      \"CABAD3\": \"oad, Ward No-04, Pourashava-Bhairab\",\n" +
                    "      \"CABAD4\": \", Upazilla/Thana-Bhairab, District-\",\n" +
                    "      \"CABAD5\": \"Kishoreganj \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"49\": {\n" +
                    "      \"CODE\": \"0088\",\n" +
                    "      \"MNEMONIC\": \"B771\",\n" +
                    "      \"NAME\": \"Lalpur-Brahmanbaria UPOSHAKHA \",\n" +
                    "      \"ROUTING\": \"120120107\",\n" +
                    "      \"CABAD1\": \"Village- Char Lalpur, Union- \",\n" +
                    "      \"CABAD2\": \"Lalpur, Thana- Ashuganj, District- \",\n" +
                    "      \"CABAD3\": \"Brahmanbaria \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"50\": {\n" +
                    "      \"CODE\": \"0089\",\n" +
                    "      \"MNEMONIC\": \"B772\",\n" +
                    "      \"NAME\": \"Araisidha Bazar Upo,Brahmanbaria \",\n" +
                    "      \"ROUTING\": \"120120107\",\n" +
                    "      \"CABAD1\": \"Hazi Achiya Hashem Shopping \",\n" +
                    "      \"CABAD2\": \"Complex, Village- Araishidha, \",\n" +
                    "      \"CABAD3\": \"Union- Araishidha, Thana- Ashuganj,\",\n" +
                    "      \"CABAD4\": \"District- Brahmanbaria \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"51\": {\n" +
                    "      \"CODE\": \"0105\",\n" +
                    "      \"MNEMONIC\": \"B788\",\n" +
                    "      \"NAME\": \"Reserve Bazar Uposhakha,Rangamati \",\n" +
                    "      \"ROUTING\": \"120840524\",\n" +
                    "      \"CABAD1\": \"Lake City Shopping Complex, House N\",\n" +
                    "      \"CABAD2\": \"o- 310, Reserve Bazar Main Road, Wa\",\n" +
                    "      \"CABAD3\": \"rd- 02 Pourashava; Rangamati, Distr\",\n" +
                    "      \"CABAD4\": \"ict- Rangamati \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"52\": {\n" +
                    "      \"CODE\": \"0106\",\n" +
                    "      \"MNEMONIC\": \"B789\",\n" +
                    "      \"NAME\": \"Debashish Nagor Upo.,Rangamati \",\n" +
                    "      \"ROUTING\": \"120840524\",\n" +
                    "      \"CABAD1\": \"Holding No- 1192/KA, Chattogram-Ran\",\n" +
                    "      \"CABAD2\": \"gamati Highway Main Road, Ward No-0\",\n" +
                    "      \"CABAD3\": \"9, Pourashava-Rangamati, Thana-Kotw\",\n" +
                    "      \"CABAD4\": \"ali, District-Rangamati. \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"53\": {\n" +
                    "      \"CODE\": \"0107\",\n" +
                    "      \"MNEMONIC\": \"B790\",\n" +
                    "      \"NAME\": \"Tabalchari Uposhakha,Rangamati \",\n" +
                    "      \"ROUTING\": \"120840524\",\n" +
                    "      \"CABAD1\": \"Holding No- 14/B, Tabalchari Road, \",\n" +
                    "      \"CABAD2\": \"Ward No- 03, Pourashava- Rangamati,\",\n" +
                    "      \"CABAD3\": \" Thana- Rangamati, District- Rangam\",\n" +
                    "      \"CABAD4\": \"ati. \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"54\": {\n" +
                    "      \"CODE\": \"0108\",\n" +
                    "      \"MNEMONIC\": \"B791\",\n" +
                    "      \"NAME\": \"Ranirhat Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120840524\",\n" +
                    "      \"CABAD1\": \"Village- Ranirhat, Union- \",\n" +
                    "      \"CABAD2\": \"Rajanagar, Thana- Rangunia, \",\n" +
                    "      \"CABAD3\": \"District- Chattogram \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"55\": {\n" +
                    "      \"CODE\": \"0109\",\n" +
                    "      \"MNEMONIC\": \"B792\",\n" +
                    "      \"NAME\": \"Dighinala,Khagrachhari \",\n" +
                    "      \"ROUTING\": \"120840524\",\n" +
                    "      \"CABAD1\": \"Village- Dighinala, Union- 2 No \",\n" +
                    "      \"CABAD2\": \"Boalkhali, Thana- Dighinala, \",\n" +
                    "      \"CABAD3\": \"District- Khagrachhari \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"56\": {\n" +
                    "      \"CODE\": \"0115\",\n" +
                    "      \"MNEMONIC\": \"B798\",\n" +
                    "      \"NAME\": \"Shantirhat Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120157633\",\n" +
                    "      \"CABAD1\": \"Kamal Mansion and Super Market, \",\n" +
                    "      \"CABAD2\": \"Chittagong-Coxs Bazar Highway, \",\n" +
                    "      \"CABAD3\": \"Ward No- 20, Pourashava- Patiya, \",\n" +
                    "      \"CABAD4\": \"Thana-Patiya, District- Chattogram.\",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"57\": {\n" +
                    "      \"CODE\": \"0116\",\n" +
                    "      \"MNEMONIC\": \"B799\",\n" +
                    "      \"NAME\": \"College Bazar Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120150522\",\n" +
                    "      \"CABAD1\": \"Village/Area- College Bazar, Union-\",\n" +
                    "      \"CABAD2\": \" Sikalbaha, Thana- Karnafuli, Distr\",\n" +
                    "      \"CABAD3\": \"ict- Chattogram. \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"58\": {\n" +
                    "      \"CODE\": \"0117\",\n" +
                    "      \"MNEMONIC\": \"B800\",\n" +
                    "      \"NAME\": \"Rawshanhat Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120157633\",\n" +
                    "      \"CABAD1\": \"Rejia Nahar Complex, Village-Kancha\",\n" +
                    "      \"CABAD2\": \"nnagar, Union-Kanchannagar, Thana-C\",\n" +
                    "      \"CABAD3\": \"handanaish, District-Chattogram \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"59\": {\n" +
                    "      \"CODE\": \"0118\",\n" +
                    "      \"MNEMONIC\": \"B801\",\n" +
                    "      \"NAME\": \"Jaldi Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120150522\",\n" +
                    "      \"CABAD1\": \"Anwara-Banshkhali Road, Ward No-05,\",\n" +
                    "      \"CABAD2\": \" Pauroshova-Banshkhali, Thana-Bansh\",\n" +
                    "      \"CABAD3\": \"khali, District-Chattogram \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"60\": {\n" +
                    "      \"CODE\": \"0119\",\n" +
                    "      \"MNEMONIC\": \"B802\",\n" +
                    "      \"NAME\": \"Kala Bibi Dighi,Chattogram \",\n" +
                    "      \"ROUTING\": \"120150522\",\n" +
                    "      \"CABAD1\": \"Member Bhaban, Village- Kala Bibi \",\n" +
                    "      \"CABAD2\": \"Dighir Moor, Union- 8 no. Chaturi, \",\n" +
                    "      \"CABAD3\": \"Thana- Anwara, District- Chattogram\",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"61\": {\n" +
                    "      \"CODE\": \"0120\",\n" +
                    "      \"MNEMONIC\": \"B803\",\n" +
                    "      \"NAME\": \"Bailchari Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120150522\",\n" +
                    "      \"CABAD1\": \"Village- Bailchari K.B. Bazar, \",\n" +
                    "      \"CABAD2\": \"Union- Bailchari, Thana- \",\n" +
                    "      \"CABAD3\": \"Banshkhali, District- Chattogram \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"62\": {\n" +
                    "      \"CODE\": \"0121\",\n" +
                    "      \"MNEMONIC\": \"B804\",\n" +
                    "      \"NAME\": \"Time Bazar Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120150522\",\n" +
                    "      \"CABAD1\": \"Area- Shilkup Barua Para, Union- \",\n" +
                    "      \"CABAD2\": \"Shilkup Mankirchar, Thana- \",\n" +
                    "      \"CABAD3\": \"Banshkhali, District- Chattogram \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"63\": {\n" +
                    "      \"CODE\": \"0122\",\n" +
                    "      \"MNEMONIC\": \"B805\",\n" +
                    "      \"NAME\": \"Rustomhat Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120150522\",\n" +
                    "      \"CABAD1\": \"Jaker Shopping Center, Village- \",\n" +
                    "      \"CABAD2\": \"Rustomhat Bottoli, Union- 4 No \",\n" +
                    "      \"CABAD3\": \"Bottoli, Thana- Anowara, District- \",\n" +
                    "      \"CABAD4\": \"Chattogram \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"64\": {\n" +
                    "      \"CODE\": \"0123\",\n" +
                    "      \"MNEMONIC\": \"B806\",\n" +
                    "      \"NAME\": \"Char Patharghata Upo, Chattogram \",\n" +
                    "      \"ROUTING\": \"120150522\",\n" +
                    "      \"CABAD1\": \"Mokka Tower, Village- Char \",\n" +
                    "      \"CABAD2\": \"Patharghata, Union- Char \",\n" +
                    "      \"CABAD3\": \"Patharghata, Thana- Karnafuli, \",\n" +
                    "      \"CABAD4\": \"District- Chattogram \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"65\": {\n" +
                    "      \"CODE\": \"0125\",\n" +
                    "      \"MNEMONIC\": \"B808\",\n" +
                    "      \"NAME\": \"Tantar Bazar Uposhakha,Brahmanbaria\",\n" +
                    "      \"ROUTING\": \"120120044\",\n" +
                    "      \"CABAD1\": \"Mosiduzzaman Bhaban, Village- Tanta\",\n" +
                    "      \"CABAD2\": \"r, Mouza- Tantar, Union- Tantar, P.\",\n" +
                    "      \"CABAD3\": \"S- Akhaura, District- Brahmanbaria \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"66\": {\n" +
                    "      \"CODE\": \"0126\",\n" +
                    "      \"MNEMONIC\": \"B809\",\n" +
                    "      \"NAME\": \"Kasba Uposhakha,Brahmanbaria \",\n" +
                    "      \"ROUTING\": \"120120044\",\n" +
                    "      \"CABAD1\": \"Fazlul Haque Tower, Holding No.-99,\",\n" +
                    "      \"CABAD2\": \" Zila Praisad Road, Ward-08, Pouros\",\n" +
                    "      \"CABAD3\": \"hava-Kasba, Thana-Kasba, District-B\",\n" +
                    "      \"CABAD4\": \"rahmanbaria \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"67\": {\n" +
                    "      \"CODE\": \"0127\",\n" +
                    "      \"MNEMONIC\": \"B810\",\n" +
                    "      \"NAME\": \"Champaknagar Upo.,Brahmanbaria \",\n" +
                    "      \"ROUTING\": \"120120044\",\n" +
                    "      \"CABAD1\": \"Five Star Plaza, Village- Champakna\",\n" +
                    "      \"CABAD2\": \"gar, Union- Bijoynagar, District- B\",\n" +
                    "      \"CABAD3\": \"rahmanbaria \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"68\": {\n" +
                    "      \"CODE\": \"0128\",\n" +
                    "      \"MNEMONIC\": \"B811\",\n" +
                    "      \"NAME\": \"Kuti Chowmohani Upo.,Brahmanbaria \",\n" +
                    "      \"ROUTING\": \"120120044\",\n" +
                    "      \"CABAD1\": \"Village-Kuti Bazar, Union-Kuti, Tha\",\n" +
                    "      \"CABAD2\": \"na-Kasba, District-Brahmanbaria \",\n" +
                    "      \"CABAD3\": \" \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"69\": {\n" +
                    "      \"CODE\": \"0129\",\n" +
                    "      \"MNEMONIC\": \"B812\",\n" +
                    "      \"NAME\": \"Nayanpur Bazar Upo,Brahmanbaria \",\n" +
                    "      \"ROUTING\": \"120120044\",\n" +
                    "      \"CABAD1\": \"Siddik Bhaban, Village/Area- \",\n" +
                    "      \"CABAD2\": \"Nayanpur Bazar, Union- Bayek, \",\n" +
                    "      \"CABAD3\": \"Thana- Kasba, District- \",\n" +
                    "      \"CABAD4\": \"Brahmanbaria \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"70\": {\n" +
                    "      \"CODE\": \"0136\",\n" +
                    "      \"MNEMONIC\": \"F565\",\n" +
                    "      \"NAME\": \"Bazalia Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120154186\",\n" +
                    "      \"CABAD1\": \"Bajalia New Market, Village/Area-Ba\",\n" +
                    "      \"CABAD2\": \"jalia Bus Station, Mouza Name-Bajal\",\n" +
                    "      \"CABAD3\": \"ia, Union-Bajalia, Thana-Satkania, \",\n" +
                    "      \"CABAD4\": \"District-Chattogram \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"71\": {\n" +
                    "      \"CODE\": \"0137\",\n" +
                    "      \"MNEMONIC\": \"F566\",\n" +
                    "      \"NAME\": \"Komol Munshir Hat,Chattogram \",\n" +
                    "      \"ROUTING\": \"120157633\",\n" +
                    "      \"CABAD1\": \"S.M. Market, Village- Komol Munshir\",\n" +
                    "      \"CABAD2\": \" Hat, Union- 16 No. Kochuai, Thana-\",\n" +
                    "      \"CABAD3\": \" Patiya, District- Chattagram \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"72\": {\n" +
                    "      \"CODE\": \"0138\",\n" +
                    "      \"MNEMONIC\": \"F567\",\n" +
                    "      \"NAME\": \"Adunagar Bazar Uposhakha,Chattogram\",\n" +
                    "      \"ROUTING\": \"120154186\",\n" +
                    "      \"CABAD1\": \"Builduing-Padmabati, Village-Adunag\",\n" +
                    "      \"CABAD2\": \"ar, Union-Adunagar, Thana-Lohagora,\",\n" +
                    "      \"CABAD3\": \" District-Chattagram \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"73\": {\n" +
                    "      \"CODE\": \"0139\",\n" +
                    "      \"MNEMONIC\": \"F568\",\n" +
                    "      \"NAME\": \"Balaghata Uposhakha,Bandarban \",\n" +
                    "      \"ROUTING\": \"120154186\",\n" +
                    "      \"CABAD1\": \"M. A. Plaza, Holding No.-377, Road \",\n" +
                    "      \"CABAD2\": \"Name-Balaghata, Ward No.-01, Pouras\",\n" +
                    "      \"CABAD3\": \"hava-Bandarban, Thana-Bandarban Sad\",\n" +
                    "      \"CABAD4\": \"ar, District-Bandarban \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"74\": {\n" +
                    "      \"CODE\": \"0140\",\n" +
                    "      \"MNEMONIC\": \"F569\",\n" +
                    "      \"NAME\": \"Dohazari Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120154186\",\n" +
                    "      \"CABAD1\": \"F.G. Habib Plaza, Rail Station Road\",\n" +
                    "      \"CABAD2\": \", Ward No.-08, Pourashava-Dohazari,\",\n" +
                    "      \"CABAD3\": \" Thana-Chandanaish, District-Chatto\",\n" +
                    "      \"CABAD4\": \"gram \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"75\": {\n" +
                    "      \"CODE\": \"0141\",\n" +
                    "      \"MNEMONIC\": \"F570\",\n" +
                    "      \"NAME\": \"Padua Uposhakha,Chattagram \",\n" +
                    "      \"ROUTING\": \"120154186\",\n" +
                    "      \"CABAD1\": \"Hazi Rashid Maket, Village/Area-Pad\",\n" +
                    "      \"CABAD2\": \"ua, Union-Padua, Thana-Lohagora, Di\",\n" +
                    "      \"CABAD3\": \"strict-Chattagram \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"76\": {\n" +
                    "      \"CODE\": \"0142\",\n" +
                    "      \"MNEMONIC\": \"F571\",\n" +
                    "      \"NAME\": \"Fultola Bazar-Satkania,Chattogram \",\n" +
                    "      \"ROUTING\": \"120154186\",\n" +
                    "      \"CABAD1\": \"Village-South Kanchona Gurguri, Uni\",\n" +
                    "      \"CABAD2\": \"on-Kanchona, Thana-Satkania, Distri\",\n" +
                    "      \"CABAD3\": \"ct-Chattogram \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"77\": {\n" +
                    "      \"CODE\": \"0143\",\n" +
                    "      \"MNEMONIC\": \"F572\",\n" +
                    "      \"NAME\": \"Mirzakhil Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120154186\",\n" +
                    "      \"CABAD1\": \"Shahin City Center, Village-Mirzakh\",\n" +
                    "      \"CABAD2\": \"il Bangla Bazar Uttar Matha, Union-\",\n" +
                    "      \"CABAD3\": \"Sonakania, Thana-Satkania, District\",\n" +
                    "      \"CABAD4\": \"-Chattogram \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"78\": {\n" +
                    "      \"CODE\": \"0144\",\n" +
                    "      \"MNEMONIC\": \"F573\",\n" +
                    "      \"NAME\": \"Chambal Bazar Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120154186\",\n" +
                    "      \"CABAD1\": \"Mannan Center, Village/Area-East Ch\",\n" +
                    "      \"CABAD2\": \"ambal, Pourashava-Chambal, Thana-Ba\",\n" +
                    "      \"CABAD3\": \"nshkhali, District-Chattogram \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"79\": {\n" +
                    "      \"CODE\": \"0145\",\n" +
                    "      \"MNEMONIC\": \"F574\",\n" +
                    "      \"NAME\": \"Gachbaria Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120154186\",\n" +
                    "      \"CABAD1\": \"Rivine Haji Plaza, Chattogram-Cox's\",\n" +
                    "      \"CABAD2\": \" Bazar Highway, Ward-08, Pauroshova\",\n" +
                    "      \"CABAD3\": \"-Chandanaish, Thana-Chandanaish, Di\",\n" +
                    "      \"CABAD4\": \"strict-Chattogram \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"80\": {\n" +
                    "      \"CODE\": \"0146\",\n" +
                    "      \"MNEMONIC\": \"F575\",\n" +
                    "      \"NAME\": \"Chunti Bazar Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120220160\",\n" +
                    "      \"CABAD1\": \"Haji Shopping Complex, Village- Chu\",\n" +
                    "      \"CABAD2\": \"nti Deputy Bazar, Union- Chunti, T\",\n" +
                    "      \"CABAD3\": \"hana- Lohagora, District- Chattogra\",\n" +
                    "      \"CABAD4\": \"m. \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"81\": {\n" +
                    "      \"CODE\": \"0147\",\n" +
                    "      \"MNEMONIC\": \"F576\",\n" +
                    "      \"NAME\": \"Matarbari Moheskhali,Cox's Bazar \",\n" +
                    "      \"ROUTING\": \"120220160\",\n" +
                    "      \"CABAD1\": \"Matarbari New Market, Village-Matar\",\n" +
                    "      \"CABAD2\": \"bari, Union-Matarbari, Thana-Mohesk\",\n" +
                    "      \"CABAD3\": \"hali, District-Cox's Bazar. \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"82\": {\n" +
                    "      \"CODE\": \"0148\",\n" +
                    "      \"MNEMONIC\": \"F577\",\n" +
                    "      \"NAME\": \"Pekua Uposhakha,Cox's Bazar \",\n" +
                    "      \"ROUTING\": \"120220160\",\n" +
                    "      \"CABAD1\": \"Sadman Market, Area-Alhaz Kabira Ah\",\n" +
                    "      \"CABAD2\": \"med Chowdhury Bazar, Union-Pekua, T\",\n" +
                    "      \"CABAD3\": \"hana-Pekua, District-Cox's Bazar \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"83\": {\n" +
                    "      \"CODE\": \"0149\",\n" +
                    "      \"MNEMONIC\": \"F578\",\n" +
                    "      \"NAME\": \"Napura Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120220160\",\n" +
                    "      \"CABAD1\": \"Smart Tower, Village-Napora Bazar, \",\n" +
                    "      \"CABAD2\": \"Union-11 No Puinchuri, Thana-Banskh\",\n" +
                    "      \"CABAD3\": \"ali, District-Chattogram. \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"84\": {\n" +
                    "      \"CODE\": \"0150\",\n" +
                    "      \"MNEMONIC\": \"F579\",\n" +
                    "      \"NAME\": \"Dulahazara,Cox's Bazar \",\n" +
                    "      \"ROUTING\": \"120220160\",\n" +
                    "      \"CABAD1\": \"Aslam Chowdhury Market Complex, \",\n" +
                    "      \"CABAD2\": \"Village-Dulahazara, \",\n" +
                    "      \"CABAD3\": \"Union-Dulahazara, Thana-Chakaria, \",\n" +
                    "      \"CABAD4\": \"District-Cox's Bazar \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"85\": {\n" +
                    "      \"CODE\": \"0176\",\n" +
                    "      \"MNEMONIC\": \"F605\",\n" +
                    "      \"NAME\": \"Manu Fakir Bazar,Chattogram \",\n" +
                    "      \"ROUTING\": \"120154186\",\n" +
                    "      \"CABAD1\": \"Boro Hatiya, Union- Boro Hatiya, \",\n" +
                    "      \"CABAD2\": \"Thana- Lohagora, District- \",\n" +
                    "      \"CABAD3\": \"Chattogram \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"86\": {\n" +
                    "      \"CODE\": \"0177\",\n" +
                    "      \"MNEMONIC\": \"F606\",\n" +
                    "      \"NAME\": \"Satghatia Pukur Par,Chattogram \",\n" +
                    "      \"ROUTING\": \"120154186\",\n" +
                    "      \"CABAD1\": \"Kabir Tower, Village- Shobondi, \",\n" +
                    "      \"CABAD2\": \"Union- 5 No. Boroma, Thana- \",\n" +
                    "      \"CABAD3\": \"Chandanaish, District- Chattogram \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"87\": {\n" +
                    "      \"CODE\": \"0178\",\n" +
                    "      \"MNEMONIC\": \"F607\",\n" +
                    "      \"NAME\": \"Lohagara Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120154186\",\n" +
                    "      \"CABAD1\": \"Al Hossain Shopping Mall, Area- \",\n" +
                    "      \"CABAD2\": \"Lohagara, Union- 6 No Lohagara, \",\n" +
                    "      \"CABAD3\": \"Thana- Lohagara, District- \",\n" +
                    "      \"CABAD4\": \"Chattogram \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"88\": {\n" +
                    "      \"CODE\": \"0179\",\n" +
                    "      \"MNEMONIC\": \"F608\",\n" +
                    "      \"NAME\": \"Satkania Uposhakha,Chattogram \",\n" +
                    "      \"ROUTING\": \"120154186\",\n" +
                    "      \"CABAD1\": \"Satkania New Market, Village/Area- \",\n" +
                    "      \"CABAD2\": \"Satkania, Union- Paschim Dhemsha, \",\n" +
                    "      \"CABAD3\": \"Thana- Satkania, District- \",\n" +
                    "      \"CABAD4\": \"Chattogram \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"89\": {\n" +
                    "      \"CODE\": \"0250\",\n" +
                    "      \"MNEMONIC\": \"C250\",\n" +
                    "      \"NAME\": \"Kadamtoli Uposhakha, Sylhet \",\n" +
                    "      \"ROUTING\": \"120913556\",\n" +
                    "      \"CABAD1\": \"Ekanto Neketon, Shornoshika \",\n" +
                    "      \"CABAD2\": \"156/157, Kadamtali, Sylhet Sadar, \",\n" +
                    "      \"CABAD3\": \"Sylhet \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"90\": {\n" +
                    "      \"CODE\": \"0251\",\n" +
                    "      \"MNEMONIC\": \"D301\",\n" +
                    "      \"NAME\": \"Jitu Miah's Point Uposhakha,Sylhet \",\n" +
                    "      \"ROUTING\": \"120913556\",\n" +
                    "      \"CABAD1\": \"Baksh Tower, V.I.P Road, Ward No- 1\",\n" +
                    "      \"CABAD2\": \"3, Sylhet City Corporation, Thana-\",\n" +
                    "      \"CABAD3\": \" Sylhet, District- Sylhet \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"91\": {\n" +
                    "      \"CODE\": \"0252\",\n" +
                    "      \"MNEMONIC\": \"D302\",\n" +
                    "      \"NAME\": \"Pirijpur Uposhakha,Sylhet \",\n" +
                    "      \"ROUTING\": \"120913556\",\n" +
                    "      \"CABAD1\": \"HR Complex, Village-Pirijpur, Unio\",\n" +
                    "      \"CABAD2\": \"n-Boroikandi, Upozila-South Surma, \",\n" +
                    "      \"CABAD3\": \"District-Sylhet. \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"92\": {\n" +
                    "      \"CODE\": \"0253\",\n" +
                    "      \"MNEMONIC\": \"D303\",\n" +
                    "      \"NAME\": \"Boroikandi Uposhakha,Sylhet \",\n" +
                    "      \"ROUTING\": \"120913556\",\n" +
                    "      \"CABAD1\": \"Habib Complex, Bangabir Road, Ward \",\n" +
                    "      \"CABAD2\": \"No-25, Sylhet City Corporation, Tha\",\n" +
                    "      \"CABAD3\": \"na-South Surma, District-Sylhet \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"93\": {\n" +
                    "      \"CODE\": \"0254\",\n" +
                    "      \"MNEMONIC\": \"D304\",\n" +
                    "      \"NAME\": \"TB Gate-Sylhet Uposhakha,Sylhet \",\n" +
                    "      \"ROUTING\": \"120913556\",\n" +
                    "      \"CABAD1\": \"RP (Rouf-Piara) Tower, Mitali R/A, \",\n" +
                    "      \"CABAD2\": \"House No.-156, TB Gate Main Road, W\",\n" +
                    "      \"CABAD3\": \"ard No.-19, Sylhet City Corporation\",\n" +
                    "      \"CABAD4\": \", Thana-Kotwali, District-Sylhet \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"94\": {\n" +
                    "      \"CODE\": \"0255\",\n" +
                    "      \"MNEMONIC\": \"D305\",\n" +
                    "      \"NAME\": \"Sylhet Station Road, Sylhet \",\n" +
                    "      \"ROUTING\": \"120913556\",\n" +
                    "      \"CABAD1\": \"Road Name-Railway Road, Ward No-26,\",\n" +
                    "      \"CABAD2\": \" Sylhet City Corporation, Thana-Sou\",\n" +
                    "      \"CABAD3\": \"th Surma, District-Sylhet \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"95\": {\n" +
                    "      \"CODE\": \"0256\",\n" +
                    "      \"MNEMONIC\": \"D306\",\n" +
                    "      \"NAME\": \"Gowainghat Bazar Uposhakha,Sylhet \",\n" +
                    "      \"ROUTING\": \"120913556\",\n" +
                    "      \"CABAD1\": \"Hazi Md. Azir Uddin Market, Area-Go\",\n" +
                    "      \"CABAD2\": \"wainghat Bazar, Union-West Jaflong,\",\n" +
                    "      \"CABAD3\": \" Thana-Gowainghat, District-Sylhet \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"96\": {\n" +
                    "      \"CODE\": \"0257\",\n" +
                    "      \"MNEMONIC\": \"D307\",\n" +
                    "      \"NAME\": \"Kanaighat Bazar Uposhakha,Sylhet \",\n" +
                    "      \"ROUTING\": \"120913556\",\n" +
                    "      \"CABAD1\": \"Madina Market, Kanaighat Road, Ward\",\n" +
                    "      \"CABAD2\": \" No.-8, Pourashava-Kanaighat, Thana\",\n" +
                    "      \"CABAD3\": \"-Kanaighat, District-Sylhet. \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"97\": {\n" +
                    "      \"CODE\": \"0258\",\n" +
                    "      \"MNEMONIC\": \"D308\",\n" +
                    "      \"NAME\": \"Jaflong Uposhakha,Sylhet \",\n" +
                    "      \"ROUTING\": \"120913556\",\n" +
                    "      \"CABAD1\": \"Labu Chairman Market, Area-Mamar \",\n" +
                    "      \"CABAD2\": \"Bazar, Union-3 No. East Jaflong, \",\n" +
                    "      \"CABAD3\": \"Thana-Gowainghat, District-Sylhet \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"98\": {\n" +
                    "      \"CODE\": \"0259\",\n" +
                    "      \"MNEMONIC\": \"D309\",\n" +
                    "      \"NAME\": \"Golapganj-Sylhet,Sylhet \",\n" +
                    "      \"ROUTING\": \"120913556\",\n" +
                    "      \"CABAD1\": \"Shams Tower, Sylhet-Zakiganj Road, \",\n" +
                    "      \"CABAD2\": \"Ward No.- 03, Pourashava- \",\n" +
                    "      \"CABAD3\": \"Golapganj, Thana- Golapganj, \",\n" +
                    "      \"CABAD4\": \"District- Sylhet \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"99\": {\n" +
                    "      \"CODE\": \"0260\",\n" +
                    "      \"MNEMONIC\": \"D311\",\n" +
                    "      \"NAME\": \"Tuker Bazar Uposhakha,Sylhet \",\n" +
                    "      \"ROUTING\": \"120913493\",\n" +
                    "      \"CABAD1\": \"Mokbul Hossain Market, Village- Sha\",\n" +
                    "      \"CABAD2\": \"hapur Khuromkhola, Tuker Bazar, Uni\",\n" +
                    "      \"CABAD3\": \"on- Tuker Bazar, Upozila- Sylhet Sa\",\n" +
                    "      \"CABAD4\": \"dar, District- Sylhet \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"100\": {\n" +
                    "      \"CODE\": \"0261\",\n" +
                    "      \"MNEMONIC\": \"D312\",\n" +
                    "      \"NAME\": \"Modina Market Uposhakha,Sylhet \",\n" +
                    "      \"ROUTING\": \"120913493\",\n" +
                    "      \"CABAD1\": \"Taj Mansion, Sylhet -Sunamganj High\",\n" +
                    "      \"CABAD2\": \"way, Ward No-09, Sylhet City Corpo\",\n" +
                    "      \"CABAD3\": \"ration, Thana-Kotowali, District-Sy\",\n" +
                    "      \"CABAD4\": \"lhet \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"101\": {\n" +
                    "      \"CODE\": \"0262\",\n" +
                    "      \"MNEMONIC\": \"D313\",\n" +
                    "      \"NAME\": \"Osmani Medical College Road ,Sylhet\",\n" +
                    "      \"ROUTING\": \"120913493\",\n" +
                    "      \"CABAD1\": \"Raj Complex, Sonar Bangla-1, Holdin\",\n" +
                    "      \"CABAD2\": \"g No-503/1, West Kajol Shah Road, W\",\n" +
                    "      \"CABAD3\": \"ard No-03, Sylhet City Corporation,\",\n" +
                    "      \"CABAD4\": \" Thana-Kotowali, District-Sylhet \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"102\": {\n" +
                    "      \"CODE\": \"0270\",\n" +
                    "      \"MNEMONIC\": \"D321\",\n" +
                    "      \"NAME\": \"Shamsher NagarUpo.,Moulvi Bazar \",\n" +
                    "      \"ROUTING\": \"120581188\",\n" +
                    "      \"CABAD1\": \"Hussain Plaza, Village- Shamsher Na\",\n" +
                    "      \"CABAD2\": \"gar, Union-4 No. Shamsher Nagar, U\",\n" +
                    "      \"CABAD3\": \"pazilla-Komolganj, Police Station- \",\n" +
                    "      \"CABAD4\": \" Shamsher Nagar-3223, Dist- Moulvi\",\n" +
                    "      \"CABAD5\": \" Bazar. \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"103\": {\n" +
                    "      \"CODE\": \"0271\",\n" +
                    "      \"MNEMONIC\": \"D322\",\n" +
                    "      \"NAME\": \"Sarkar Bazar Bus Stand, Moulvibazar\",\n" +
                    "      \"ROUTING\": \"120581188\",\n" +
                    "      \"CABAD1\": \"Village-Sarkar Bazar, Union-2 No. M\",\n" +
                    "      \"CABAD2\": \"anumukh, Thana-Moulvibazar Sadar, D\",\n" +
                    "      \"CABAD3\": \"istrict-Moulvibazar \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"104\": {\n" +
                    "      \"CODE\": \"0272\",\n" +
                    "      \"MNEMONIC\": \"D323\",\n" +
                    "      \"NAME\": \"Tengra Bazar,Moulvibazar \",\n" +
                    "      \"ROUTING\": \"120581188\",\n" +
                    "      \"CABAD1\": \"Haque Plaza, Village- Tengra Bazar,\",\n" +
                    "      \"CABAD2\": \"Union- 6 no Tengra, Thana- \",\n" +
                    "      \"CABAD3\": \"Rajnagar, District- Moulvibazar \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"105\": {\n" +
                    "      \"CODE\": \"0280\",\n" +
                    "      \"MNEMONIC\": \"D331\",\n" +
                    "      \"NAME\": \"Adampur Bazar Uposhakha,Moulvibazar\",\n" +
                    "      \"ROUTING\": \"120581720\",\n" +
                    "      \"CABAD1\": \"Jalal Market, Village-Adampur Bazar\",\n" +
                    "      \"CABAD2\": \", Union-7 no. Adampur, Thana-Kamalg\",\n" +
                    "      \"CABAD3\": \"anj, District- Moulvibazar \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"106\": {\n" +
                    "      \"CODE\": \"0281\",\n" +
                    "      \"MNEMONIC\": \"D332\",\n" +
                    "      \"NAME\": \"Bhoirab Bazar-Sreemangal \",\n" +
                    "      \"ROUTING\": \"120581720\",\n" +
                    "      \"CABAD1\": \"Village-Majdihi, Union-Kalapur, Tha\",\n" +
                    "      \"CABAD2\": \"na-Sreemangal, District-Moulvibazar\",\n" +
                    "      \"CABAD3\": \" \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"107\": {\n" +
                    "      \"CODE\": \"0282\",\n" +
                    "      \"MNEMONIC\": \"D333\",\n" +
                    "      \"NAME\": \"Kamalganj Uposhakha,Moulvi Bazar \",\n" +
                    "      \"ROUTING\": \"120581720\",\n" +
                    "      \"CABAD1\": \"Village-Vanugach, Union-Vanugach, T\",\n" +
                    "      \"CABAD2\": \"hana-Komolganj, District-Moulvi Baz\",\n" +
                    "      \"CABAD3\": \"ar \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"108\": {\n" +
                    "      \"CODE\": \"0290\",\n" +
                    "      \"MNEMONIC\": \"D341\",\n" +
                    "      \"NAME\": \"Shibganj Bazar Uposhakha,Sylhet \",\n" +
                    "      \"ROUTING\": \"120914034\",\n" +
                    "      \"CABAD1\": \"Nahar Tower, Holding No- 760, Tamab\",\n" +
                    "      \"CABAD2\": \"il Road, Ward No- 21, Sylhet City C\",\n" +
                    "      \"CABAD3\": \"orporation, Thana- Shahporan, Distr\",\n" +
                    "      \"CABAD4\": \"ict- Sylhet \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"109\": {\n" +
                    "      \"CODE\": \"0291\",\n" +
                    "      \"MNEMONIC\": \"D342\",\n" +
                    "      \"NAME\": \"Fenchuganj Uposhakha,Sylhet \",\n" +
                    "      \"ROUTING\": \"120914034\",\n" +
                    "      \"CABAD1\": \"Monir & Raja Shopping Complex, Vill\",\n" +
                    "      \"CABAD2\": \"age-Fenchuganj Bazar, Union-1 no Fe\",\n" +
                    "      \"CABAD3\": \"nchuganj, Thana-Fenchuganj, Distric\",\n" +
                    "      \"CABAD4\": \"t-Sylhet \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"110\": {\n" +
                    "      \"CODE\": \"0292\",\n" +
                    "      \"MNEMONIC\": \"D343\",\n" +
                    "      \"NAME\": \"Lala Bazar Uposhakha, Sylhet \",\n" +
                    "      \"ROUTING\": \"120914034\",\n" +
                    "      \"CABAD1\": \"Hazi Rois Mia Shopping complex, \",\n" +
                    "      \"CABAD2\": \"Area- Lala Bazar, Union- 6 No. Lala\",\n" +
                    "      \"CABAD3\": \"Bazar, Thana- South Surma, \",\n" +
                    "      \"CABAD4\": \"District- Sylhet \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"111\": {\n" +
                    "      \"CODE\": \"0300\",\n" +
                    "      \"MNEMONIC\": \"D351\",\n" +
                    "      \"NAME\": \"Shahabazpur Uposhakha,MoulviBazar \",\n" +
                    "      \"ROUTING\": \"120910317\",\n" +
                    "      \"CABAD1\": \"Taslim Uddin Shopping Mahal, Villag\",\n" +
                    "      \"CABAD2\": \"e- Shahabajpur, Union- 4 North Shah\",\n" +
                    "      \"CABAD3\": \"abajpur, Upozila- Borolekha, Distri\",\n" +
                    "      \"CABAD4\": \"ct- Moulvi Bazar. \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"112\": {\n" +
                    "      \"CODE\": \"0301\",\n" +
                    "      \"MNEMONIC\": \"D352\",\n" +
                    "      \"NAME\": \"Ashirganj Bazar Uposhakha,Sylhet \",\n" +
                    "      \"ROUTING\": \"120910317\",\n" +
                    "      \"CABAD1\": \"City Shoppy Complex, Area- \",\n" +
                    "      \"CABAD2\": \"Ashirganj Bazar, Union- 10 No North\",\n" +
                    "      \"CABAD3\": \"Badepasha, Thana- Golapganj, \",\n" +
                    "      \"CABAD4\": \"District- Sylhet \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"113\": {\n" +
                    "      \"CODE\": \"0310\",\n" +
                    "      \"MNEMONIC\": \"D361\",\n" +
                    "      \"NAME\": \"Majortila Uposhakha,Sylhet \",\n" +
                    "      \"ROUTING\": \"120913972\",\n" +
                    "      \"CABAD1\": \"Siddiqueyi Mansion, Village- Islamp\",\n" +
                    "      \"CABAD2\": \"ur, Union- 4 No. Khadimpara, Thana-\",\n" +
                    "      \"CABAD3\": \" Shahporan, District- Sylhet \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"114\": {\n" +
                    "      \"CODE\": \"0311\",\n" +
                    "      \"MNEMONIC\": \"D362\",\n" +
                    "      \"NAME\": \"Khasdobir Point Uposhakha,Sylhet \",\n" +
                    "      \"ROUTING\": \"120913972\",\n" +
                    "      \"CABAD1\": \"Amin Tower, Holding No-241, Airport\",\n" +
                    "      \"CABAD2\": \" Road, Ward No-05, Sylhet City Corp\",\n" +
                    "      \"CABAD3\": \"oration, Upazilla-Shylet Sadar, Tha\",\n" +
                    "      \"CABAD4\": \"na-Sylhet, District-Sylhet \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"115\": {\n" +
                    "      \"CODE\": \"0312\",\n" +
                    "      \"MNEMONIC\": \"D363\",\n" +
                    "      \"NAME\": \"Lama Bazar Uposhakha,Sylhet \",\n" +
                    "      \"ROUTING\": \"120913972\",\n" +
                    "      \"CABAD1\": \"H.F. Center, Lama Bazar Road, Ward \",\n" +
                    "      \"CABAD2\": \"No-02, Sylhet City Corporation, Tha\",\n" +
                    "      \"CABAD3\": \"na-Kotowali, District-Sylhet \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"116\": {\n" +
                    "      \"CODE\": \"0320\",\n" +
                    "      \"MNEMONIC\": \"D371\",\n" +
                    "      \"NAME\": \"Tajpur Uposhakha,Sylhet \",\n" +
                    "      \"ROUTING\": \"120911545\",\n" +
                    "      \"CABAD1\": \"Irshad Ali Shopping City, Village-T\",\n" +
                    "      \"CABAD2\": \"ajpur, Union-Tajpur, Upozila-Osmani\",\n" +
                    "      \"CABAD3\": \" Nagar, District-Sylhet \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"117\": {\n" +
                    "      \"CODE\": \"0321\",\n" +
                    "      \"MNEMONIC\": \"D372\",\n" +
                    "      \"NAME\": \"Khadimpur Bazar Uposhakha, Sylhet \",\n" +
                    "      \"ROUTING\": \"120911545\",\n" +
                    "      \"CABAD1\": \"Al Mobaraka Shopping Center, Villag\",\n" +
                    "      \"CABAD2\": \"e-Khadimpur, Union-Omarpur, Thana-O\",\n" +
                    "      \"CABAD3\": \"smani Nagar, District- Sylhet \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"118\": {\n" +
                    "      \"CODE\": \"0322\",\n" +
                    "      \"MNEMONIC\": \"D373\",\n" +
                    "      \"NAME\": \"Sherpur-Sylhet,Moulvibazar \",\n" +
                    "      \"ROUTING\": \"120911545\",\n" +
                    "      \"CABAD1\": \"Halima & Naima Complex, Village-She\",\n" +
                    "      \"CABAD2\": \"rpur, Union-Sherpur, Thana-Moulviba\",\n" +
                    "      \"CABAD3\": \"zar Sadar, District-Moulvibazar \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"119\": {\n" +
                    "      \"CODE\": \"0325\",\n" +
                    "      \"MNEMONIC\": \"C325\",\n" +
                    "      \"NAME\": \"Shahestaganj Bazar Uposhakha \",\n" +
                    "      \"ROUTING\": \"120360617\",\n" +
                    "      \"CABAD1\": \"\\\"Holding No-0324-00, Daudnagar \",\n" +
                    "      \"CABAD2\": \"Bazar, Shahestaganj Pourashava, \",\n" +
                    "      \"CABAD3\": \"Habiganj Sadar, Habiganj \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"120\": {\n" +
                    "      \"CODE\": \"0326\",\n" +
                    "      \"MNEMONIC\": \"D376\",\n" +
                    "      \"NAME\": \"Olipur Highway Uposhakha \",\n" +
                    "      \"ROUTING\": \"120360617\",\n" +
                    "      \"CABAD1\": \"Sardar Complex, Village-Olipur, Dag\",\n" +
                    "      \"CABAD2\": \"No-163, Khatian No- 133, Mouja- \",\n" +
                    "      \"CABAD3\": \"Olipur, Union- 11 No- Brahamandura,\",\n" +
                    "      \"CABAD4\": \"Shaestaganj, Hobiganj \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"121\": {\n" +
                    "      \"CODE\": \"0327\",\n" +
                    "      \"MNEMONIC\": \"D377\",\n" +
                    "      \"NAME\": \"Chunarughat Uposhakha \",\n" +
                    "      \"ROUTING\": \"120360617\",\n" +
                    "      \"CABAD1\": \"Nironjon City Market, Holding No- \",\n" +
                    "      \"CABAD2\": \"095/00, Road Name- Moddho Bazar \",\n" +
                    "      \"CABAD3\": \"Road, Upazilla- Chunarughat, \",\n" +
                    "      \"CABAD4\": \"District- Habiganj \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"122\": {\n" +
                    "      \"CODE\": \"0328\",\n" +
                    "      \"MNEMONIC\": \"D378\",\n" +
                    "      \"NAME\": \"Court Station Road Uposhakha,Habi. \",\n" +
                    "      \"ROUTING\": \"120360617\",\n" +
                    "      \"CABAD1\": \"Alif Atraf Center, Holding No- \",\n" +
                    "      \"CABAD2\": \"6463-6464, Court Station Road, Ward\",\n" +
                    "      \"CABAD3\": \"No- 09, Habiganj Pourashava, \",\n" +
                    "      \"CABAD4\": \"Upazilla- Habiganj Sadar, District-\",\n" +
                    "      \"CABAD5\": \"Habiganj \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"123\": {\n" +
                    "      \"CODE\": \"0329\",\n" +
                    "      \"MNEMONIC\": \"D379\",\n" +
                    "      \"NAME\": \"Nabiganj Upozilla,Habiganj \",\n" +
                    "      \"ROUTING\": \"120360617\",\n" +
                    "      \"CABAD1\": \"Sherpur Road, Ward No-8, \",\n" +
                    "      \"CABAD2\": \"Pourashava-Nabiganj, \",\n" +
                    "      \"CABAD3\": \"Thana-Nabiganj, \",\n" +
                    "      \"CABAD4\": \"District-Habiganj \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"124\": {\n" +
                    "      \"CODE\": \"0330\",\n" +
                    "      \"MNEMONIC\": \"D380\",\n" +
                    "      \"NAME\": \"Mirpur Bazar Uposhakha,Habiganj \",\n" +
                    "      \"ROUTING\": \"120360617\",\n" +
                    "      \"CABAD1\": \"Ibrahim Plaza, Village-Mirpur Bazar\",\n" +
                    "      \"CABAD2\": \", Union-Mirpur Bazar, Upozila-Bahu\",\n" +
                    "      \"CABAD3\": \"bal, District-Habiganj \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"125\": {\n" +
                    "      \"CODE\": \"0331\",\n" +
                    "      \"MNEMONIC\": \"D381\",\n" +
                    "      \"NAME\": \"Baniachang Uposhakha,Habiganj \",\n" +
                    "      \"ROUTING\": \"120360617\",\n" +
                    "      \"CABAD1\": \"Abul Hossain Mansion, Village-Nandi\",\n" +
                    "      \"CABAD2\": \"para, Union-1 No Uttarpurbo Union, \",\n" +
                    "      \"CABAD3\": \"District-Habiganj \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"126\": {\n" +
                    "      \"CODE\": \"0332\",\n" +
                    "      \"MNEMONIC\": \"D382\",\n" +
                    "      \"NAME\": \"Umednagar Shilpa Elaka, Habiganj \",\n" +
                    "      \"ROUTING\": \"120360617\",\n" +
                    "      \"CABAD1\": \"Badar Jahan Complex, Holding No- 11\",\n" +
                    "      \"CABAD2\": \"44/1, Habiganj-Baniachong Road, War\",\n" +
                    "      \"CABAD3\": \"d No- 02, Pourashava- Habiganj, Tha\",\n" +
                    "      \"CABAD4\": \"na- Habiganj, District- Habiganj \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"127\": {\n" +
                    "      \"CODE\": \"0333\",\n" +
                    "      \"MNEMONIC\": \"D383\",\n" +
                    "      \"NAME\": \"Aushkandi Uposhakha,Habiganj \",\n" +
                    "      \"ROUTING\": \"120360617\",\n" +
                    "      \"CABAD1\": \"Rahman Complex, Village-Aushkandi B\",\n" +
                    "      \"CABAD2\": \"azar, Union-05, Aushkani, Thana-Nab\",\n" +
                    "      \"CABAD3\": \"iganj, District-Habiganj. \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"128\": {\n" +
                    "      \"CODE\": \"0334\",\n" +
                    "      \"MNEMONIC\": \"D384\",\n" +
                    "      \"NAME\": \"Asampara Bazar Uposhakha,Habiganj \",\n" +
                    "      \"ROUTING\": \"120360617\",\n" +
                    "      \"CABAD1\": \"Sheikh Mansion, Village-Gazipur, Un\",\n" +
                    "      \"CABAD2\": \"ion-1 No. Gazipur, Thana-Chunarugha\",\n" +
                    "      \"CABAD3\": \"t, District-Habiganj. \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"129\": {\n" +
                    "      \"CODE\": \"0335\",\n" +
                    "      \"MNEMONIC\": \"D386\",\n" +
                    "      \"NAME\": \"Phandauk Bazar Upo.,Brahmanbaria \",\n" +
                    "      \"ROUTING\": \"120360888\",\n" +
                    "      \"CABAD1\": \"Phandauk Bazar Jame Masjid Market, \",\n" +
                    "      \"CABAD2\": \"Village-Phandauk, Union-Phandauk, U\",\n" +
                    "      \"CABAD3\": \"pozila-Nasirnagar, District-Brahman\",\n" +
                    "      \"CABAD4\": \"baria \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"130\": {\n" +
                    "      \"CODE\": \"0336\",\n" +
                    "      \"MNEMONIC\": \"D387\",\n" +
                    "      \"NAME\": \"Noapara Bazar Uposhakha,Habiganj \",\n" +
                    "      \"ROUTING\": \"120360888\",\n" +
                    "      \"CABAD1\": \"Noorjahan Market, Village-Noapara, \",\n" +
                    "      \"CABAD2\": \"Union-Etakhola, Upozila-Madhabpur, \",\n" +
                    "      \"CABAD3\": \"District-Habiganj \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"131\": {\n" +
                    "      \"CODE\": \"0337\",\n" +
                    "      \"MNEMONIC\": \"D388\",\n" +
                    "      \"NAME\": \"Montola Bazar Uposhakha,Habiganj \",\n" +
                    "      \"ROUTING\": \"120360888\",\n" +
                    "      \"CABAD1\": \"Village- Afjalpur, Union- 3 No. Boh\",\n" +
                    "      \"CABAD2\": \"ora, Upozila- Madhabpur, District- \",\n" +
                    "      \"CABAD3\": \"Habiganj \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"132\": {\n" +
                    "      \"CODE\": \"0338\",\n" +
                    "      \"MNEMONIC\": \"D389\",\n" +
                    "      \"NAME\": \"Dharmaghar Bazar Uposhakha,Habiganj\",\n" +
                    "      \"ROUTING\": \"120360888\",\n" +
                    "      \"CABAD1\": \"Obeaydullah Complex, Area-Dharmagha\",\n" +
                    "      \"CABAD2\": \"r Maddho Bazar, Union-Dharmaghar, T\",\n" +
                    "      \"CABAD3\": \"hana-Madhabpur, District-Habiganj \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"133\": {\n" +
                    "      \"CODE\": \"0339\",\n" +
                    "      \"MNEMONIC\": \"D390\",\n" +
                    "      \"NAME\": \"Chatian-Madhabpur,Habiganj \",\n" +
                    "      \"ROUTING\": \"120360888\",\n" +
                    "      \"CABAD1\": \"Sayed Morshad kamal Market, Village\",\n" +
                    "      \"CABAD2\": \"/Area-Chatian, Union-Chatian, Than\",\n" +
                    "      \"CABAD3\": \"a-Madhabpur, District-Habiganj \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"134\": {\n" +
                    "      \"CODE\": \"0340\",\n" +
                    "      \"MNEMONIC\": \"D391\",\n" +
                    "      \"NAME\": \"Choumuhani Bazar-Madhabpur,Habiganj\",\n" +
                    "      \"ROUTING\": \"120360888\",\n" +
                    "      \"CABAD1\": \"Village-Alaboxpur, Union-2 No Choum\",\n" +
                    "      \"CABAD2\": \"uhani, Thana-Madhabpur, District-Ha\",\n" +
                    "      \"CABAD3\": \"biganj \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"135\": {\n" +
                    "      \"CODE\": \"0345\",\n" +
                    "      \"MNEMONIC\": \"D396\",\n" +
                    "      \"NAME\": \"Barlekha Uposhakha,Moulvibazar \",\n" +
                    "      \"ROUTING\": \"120580671\",\n" +
                    "      \"CABAD1\": \"Hazi Memorial Market, Barlekha Road\",\n" +
                    "      \"CABAD2\": \", Ward No-04, Pourashava-Barlekha, \",\n" +
                    "      \"CABAD3\": \"Upozilla-Baralekha, Thana-Baralekha\",\n" +
                    "      \"CABAD4\": \", District-Moulvibazar \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"136\": {\n" +
                    "      \"CODE\": \"0346\",\n" +
                    "      \"MNEMONIC\": \"D397\",\n" +
                    "      \"NAME\": \"Kulaura Uposhakha,Moulvi Bazar \",\n" +
                    "      \"ROUTING\": \"120580671\",\n" +
                    "      \"CABAD1\": \"S.A. Shopping Center, Holding No- 2\",\n" +
                    "      \"CABAD2\": \"17, Kulaura Borolekha Highway, Ward\",\n" +
                    "      \"CABAD3\": \" No- 05, Kulaura Pourashava, Thana-\",\n" +
                    "      \"CABAD4\": \" Kulaura, District- Moulvi Bazar \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"137\": {\n" +
                    "      \"CODE\": \"0347\",\n" +
                    "      \"MNEMONIC\": \"D398\",\n" +
                    "      \"NAME\": \"Brahman Bazar Upo,Moulvi Bazar \",\n" +
                    "      \"ROUTING\": \"120580671\",\n" +
                    "      \"CABAD1\": \"M A Market, Area- Brahman Bazar, \",\n" +
                    "      \"CABAD2\": \"Union- 5 No. Brahman Bazar, Thana- \",\n" +
                    "      \"CABAD3\": \"Kulaura, District- Moulvi Bazar \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"138\": {\n" +
                    "      \"CODE\": \"0351\",\n" +
                    "      \"MNEMONIC\": \"F680\",\n" +
                    "      \"NAME\": \"Gabindaganj-Sunamganj Uposhakha \",\n" +
                    "      \"ROUTING\": \"120901126\",\n" +
                    "      \"CABAD1\": \"Abdur Razzak Market, Village- \",\n" +
                    "      \"CABAD2\": \"Gabindaganj, Union- Gabindaganj \",\n" +
                    "      \"CABAD3\": \"Duidhargaon, Thana- Chattak, \",\n" +
                    "      \"CABAD4\": \"District- Sunamganj \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"139\": {\n" +
                    "      \"CODE\": \"0361\",\n" +
                    "      \"MNEMONIC\": \"F690\",\n" +
                    "      \"NAME\": \"Puran Munsafi Road Upo,Habiganj \",\n" +
                    "      \"ROUTING\": \"120360617\",\n" +
                    "      \"CABAD1\": \"Sharif Complex, Holding No- 3656, \",\n" +
                    "      \"CABAD2\": \"Puran Munsafi Road, Ward No- 05, \",\n" +
                    "      \"CABAD3\": \"Pauroshova- Habiganj, Thana- \",\n" +
                    "      \"CABAD4\": \"Habiganj, District- Habiganj \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"140\": {\n" +
                    "      \"CODE\": \"0371\",\n" +
                    "      \"MNEMONIC\": \"F700\",\n" +
                    "      \"NAME\": \"Jaintapur Uposhakha,Sylhet \",\n" +
                    "      \"ROUTING\": \"120913556\",\n" +
                    "      \"CABAD1\": \"Maa Market, Village- Jaintapur, \",\n" +
                    "      \"CABAD2\": \"Union- Jaintapur, Thana- Jaintapur,\",\n" +
                    "      \"CABAD3\": \"District- Sylhet \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"141\": {\n" +
                    "      \"CODE\": \"0400\",\n" +
                    "      \"MNEMONIC\": \"B635\",\n" +
                    "      \"NAME\": \"Nathullahbad Uposhakha,Barisal \",\n" +
                    "      \"ROUTING\": \"120060283\",\n" +
                    "      \"CABAD1\": \"Holding No-2936, Khalpar Road, Ward\",\n" +
                    "      \"CABAD2\": \" No-29, Barisal City Corporation, T\",\n" +
                    "      \"CABAD3\": \"hana-Biman Bandar, District-Barisal\",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"142\": {\n" +
                    "      \"CODE\": \"0401\",\n" +
                    "      \"MNEMONIC\": \"B636\",\n" +
                    "      \"NAME\": \"Rupatali Uposhakha,Barisal \",\n" +
                    "      \"ROUTING\": \"120060283\",\n" +
                    "      \"CABAD1\": \"Haider Mansion, Holding No.-437/1, \",\n" +
                    "      \"CABAD2\": \"Barisal Jhalakathi Road, Ward No.-2\",\n" +
                    "      \"CABAD3\": \"5, Barisal City Corporation, Thana-\",\n" +
                    "      \"CABAD4\": \"Barisal Sadar, District-Barisal \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"143\": {\n" +
                    "      \"CODE\": \"0402\",\n" +
                    "      \"MNEMONIC\": \"B637\",\n" +
                    "      \"NAME\": \"Barishal Bazar Road Uposhakha \",\n" +
                    "      \"ROUTING\": \"120060283\",\n" +
                    "      \"CABAD1\": \"Holding No-309, Bazar Road, Ward No\",\n" +
                    "      \"CABAD2\": \"-08, Barishal City Corporation, Tha\",\n" +
                    "      \"CABAD3\": \"na-Kawnia, District-Barishal \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"144\": {\n" +
                    "      \"CODE\": \"0403\",\n" +
                    "      \"MNEMONIC\": \"B638\",\n" +
                    "      \"NAME\": \"Barishal Chawk Bazar Upo.,Barishal \",\n" +
                    "      \"ROUTING\": \"120060283\",\n" +
                    "      \"CABAD1\": \"Building Name-Hotel One Tower, Hold\",\n" +
                    "      \"CABAD2\": \"ing No.-653, Hazrat Eyanetur Rahman\",\n" +
                    "      \"CABAD3\": \" Road, Ward No-09, Barishal City C\",\n" +
                    "      \"CABAD4\": \"orporation, Thana-Kotwali, District\",\n" +
                    "      \"CABAD5\": \"-Barishal \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"145\": {\n" +
                    "      \"CODE\": \"0404\",\n" +
                    "      \"MNEMONIC\": \"B639\",\n" +
                    "      \"NAME\": \"Dhamura Bandar Uposhakha,Barishal \",\n" +
                    "      \"ROUTING\": \"120060283\",\n" +
                    "      \"CABAD1\": \"Dr. Golam Kibira Bhaban, \",\n" +
                    "      \"CABAD2\": \"Village-Dhamura, Union-Sholak, \",\n" +
                    "      \"CABAD3\": \"Thana-Wazirpur, \",\n" +
                    "      \"CABAD4\": \"District-Barishal \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"146\": {\n" +
                    "      \"CODE\": \"0405\",\n" +
                    "      \"MNEMONIC\": \"B640\",\n" +
                    "      \"NAME\": \"Chowmatha Uposhakha,Barishal \",\n" +
                    "      \"ROUTING\": \"120060283\",\n" +
                    "      \"CABAD1\": \"Mujib Complex, Holding No.-451, C &\",\n" +
                    "      \"CABAD2\": \" B Road, Ward No.-21, Barishal City\",\n" +
                    "      \"CABAD3\": \" Corporation , Thana-Kotwali Model \",\n" +
                    "      \"CABAD4\": \"Thana, District-Barishal \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"147\": {\n" +
                    "      \"CODE\": \"0406\",\n" +
                    "      \"MNEMONIC\": \"B641\",\n" +
                    "      \"NAME\": \"Kalaya Bandar Uposhakha,Patuakhali \",\n" +
                    "      \"ROUTING\": \"120781096\",\n" +
                    "      \"CABAD1\": \"Village/Area- Kalaya Bandar, Dag \",\n" +
                    "      \"CABAD2\": \"No.- S.A. 256/12/13, Khatian No.- \",\n" +
                    "      \"CABAD3\": \"S.A. 948, 958, Mouza Name- Kalaya, \",\n" +
                    "      \"CABAD4\": \"Union- Kalaya, Thana- Baufal, \",\n" +
                    "      \"CABAD5\": \"District- Patuakhali. \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"148\": {\n" +
                    "      \"CODE\": \"0407\",\n" +
                    "      \"MNEMONIC\": \"B642\",\n" +
                    "      \"NAME\": \"Bauphal Bazar Uposhakha,Patuakhali \",\n" +
                    "      \"ROUTING\": \"120781096\",\n" +
                    "      \"CABAD1\": \"Talukder Bhaban, Holding No.- 63, \",\n" +
                    "      \"CABAD2\": \"Road Name- Baufal Bazar Road, Ward \",\n" +
                    "      \"CABAD3\": \"No.- 04, Pourosova- Baufal, Thana- \",\n" +
                    "      \"CABAD4\": \"Baufal, District- Patuakhali. \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"149\": {\n" +
                    "      \"CODE\": \"0408\",\n" +
                    "      \"MNEMONIC\": \"B643\",\n" +
                    "      \"NAME\": \"Bangla Bazar-Barishal,Barishal \",\n" +
                    "      \"ROUTING\": \"120060283\",\n" +
                    "      \"CABAD1\": \"Holding No-56350, Bapist Mission Ro\",\n" +
                    "      \"CABAD2\": \"ad, Ward No-11, Barishal City Corpo\",\n" +
                    "      \"CABAD3\": \"ration, Thana-Barishal Sadar, Distr\",\n" +
                    "      \"CABAD4\": \"ict-Barishal. \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    },\n" +
                    "    \"150\": {\n" +
                    "      \"CODE\": \"0409\",\n" +
                    "      \"MNEMONIC\": \"B644\",\n" +
                    "      \"NAME\": \"Shikarpur Uposhakha,Barishal \",\n" +
                    "      \"ROUTING\": \"120060283\",\n" +
                    "      \"CABAD1\": \"Munshi Super Market, Area-Shikarpur\",\n" +
                    "      \"CABAD2\": \", Union-Shikarpur, Thana-Ujirpur, D\",\n" +
                    "      \"CABAD3\": \"istrict-Barishal. \",\n" +
                    "      \"CABAD4\": \" \",\n" +
                    "      \"CABAD5\": \" \",\n" +
                    "      \"BRANCHBOOTH\": \"BTH\"\n" +
                    "    }\n" +
                    "  }\n" +
                    "}";

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);

            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
            String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
            branchAPIResponse = mapper.readValue(jsonStr, BranchAPIResponse.class);
            System.out.println(branchAPIResponse);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new BranchAPIResponse();
        }
        return branchAPIResponse;
    }
}
