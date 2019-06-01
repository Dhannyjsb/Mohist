package red.mohist.update;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.bukkit.Bukkit;
import red.mohist.Mohist;
import red.mohist.MohistConfig;
import red.mohist.down.Download;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Update implements Runnable{

    private static String pre = "��c��l[��bMohist���³����c��l] ��b";

    public static boolean getUpdate(){
        JSONObject json = getJson();
        String version = json.getString("name");
        if(!version.equals(Mohist.getVersion())) {
            Mohist.LOGGER.info(pre + "���ָ���,���°汾��Ϊ: ��7(��e" + version + "��7) ��bĿǰ�汾Ϊ: ��7(��e" + Mohist.getVersion() + "��7)");
            return true;
        }
        Mohist.LOGGER.info(pre + "û�з��ָ���,���°汾��Ϊ: ��7(��e" + version + "��7) ��bĿǰ�汾Ϊ: ��7(��e" + Mohist.getVersion() + "��7)");
        Mohist.LOGGER.info(pre + "����㲻�����ø��¼�⣬����mohist.yml���update��autoget��Ϊflase");
        return false;
    }

    public static void downloadUpdate() {
        JSONObject json = getJson();

        String version = json.getString("name");
        String releasesPeople = json.getJSONObject("author").getString("login");

        JSONArray ja = json.getJSONArray("assets");
        int size = ja.size();
        String releasesDate = json.getString("created_at").replaceAll("T","T ");
        String releasesMsg = json.getString("body");
        Mohist.LOGGER.info(pre + "���� ��e" + size + "��b ���ļ�");
        for (int i = 0;i < size;i++){
            Mohist.LOGGER.info(pre + "��ʼ����...");
            new Download(ja.getJSONObject(i).getString("browser_download_url"),"Mohist-update.jar",true);
            Mohist.LOGGER.info(pre + "������Ϣ: ��e" + releasesMsg);
            Mohist.LOGGER.info(pre + "��������: ��e" + releasesDate);
        }


    }

    private static JSONObject getJson(){
        //�����url
        URL url = null;
        //������http����
        HttpURLConnection httpConn = null;
        //�����������
        BufferedReader in = null;
        //�������Ļ���
        StringBuffer sb = new StringBuffer();
        try{
            url = new URL("https://api.github.com/repos/PFCraft/Mohist/releases/latest");
            in = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8") );
            String str = null;
            //һ��һ�н��ж���
            while((str = in.readLine()) != null) {
                sb.append( str );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally{
            try{
                if(in!=null) {
                    in.close(); //�ر���
                }
            }catch(IOException ex) {
                ex.printStackTrace();
            }
        }
        String jsonText =sb.toString();
        return JSON.parseObject(jsonText);
    }

    @Override
    public void run() {
        if(MohistConfig.config.getBoolean("update.autoget")){
            if(Update.getUpdate()){
                Mohist.LOGGER.info(pre + "��Ϊ���ں�̨���ظ���...");
                if(!new File("Mohist-update.jar").exists()){
                    Update.downloadUpdate();
                }
                Mohist.LOGGER.info(pre + "�������,�뽫��Ŀ¼�µ� Mohist-update.jar�ļ��滻���˲�ɾ��Mohist-update.jar����libraries.zip��ѹ�滻libraries�ļ���");
            }
        }else
            Mohist.LOGGER.info(pre + "�Զ�����δ����");
    }
}
