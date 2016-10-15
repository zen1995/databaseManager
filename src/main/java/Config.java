import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	private static final String filePath = System.getProperty("user.dir") + "/config.properties";
	private Properties properties;
	private static Config instance = null;
	private Config() throws Exception{
		File file = new File(filePath);
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			properties = new Properties();
			properties.setProperty("imageBaseDir", "");
			FileOutputStream outputFile = new FileOutputStream(filePath);
			properties.store(outputFile, "");
			outputFile.close();

		} else {
			properties = new Properties();
			InputStream in = new FileInputStream(filePath);		
			properties.load(in);
			in.close();
		}
	}

	public static synchronized Config getInstance() {
		if(instance == null){
			try {
				instance = new Config();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return instance;
	}

}
