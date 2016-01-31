import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*
 * 敵の行動情報を取得するクラス
 * csv形式のファイルを読み取る
 */
public class EnemyController {
	File csvFile = null;
	ArrayList<EnemyData> schedule;

	//敵情報を格納するための配列を作成する
	EnemyController(){
		schedule = new ArrayList<EnemyData>();
	}

	//獲得した敵の行動情報を渡す
	ArrayList<EnemyData> GetList(){
		return schedule;
	}

	//csv形式のファイルを読み取る
	boolean loadFile(String FileName){
		//敵行動ファイルを読み込む
		csvFile = new File(FileName);

		try {
			BufferedReader br = new BufferedReader(new FileReader(csvFile));

			//最終行まで読み込む
			String line = "";

			//列の情報を読み取っていく
			while((line = br.readLine()) != null){

				//ファイルバッファ
				int flag = 0;
				int AppTime = 0;
				float x,y,vx,vy;
				int HP,ShotType;
				float BulletSpeed;
				int BulletWay;
				float EX[] = new float[5];
				x = 0;
				y = 0;
				vx = 0;
				vy = 0;
				HP = 0;
				ShotType = 0;
				BulletSpeed = 0;
				BulletWay = 0;

				//1行をデータの要素に分割
				StringTokenizer st = new StringTokenizer(line,",");

			/*
			 * 		ファイルフォーマット
			 * 		読み込みフラグ[int]//0の場合は読み込まない
			 * 		出現タイミング[int]
			 * 		初期座標x[float]
			 * 		初期座標y[float]
			 * 		x座標速度[float]
			 * 		y座標速度[float]
			 * 		HP[int]
			 * 		弾幕種類[int]
			 * 		弾の速度[float]
			 * 		弾道数[int]
			 */
				//読み取り許可のフラグを獲得する
				flag = Integer.parseInt(st.nextToken());
				if(flag >=1){

					AppTime = Integer.parseInt(st.nextToken());
					x = Float.parseFloat(st.nextToken());
					y = Float.parseFloat(st.nextToken());
					vx = Float.parseFloat(st.nextToken());
					vy  = Float.parseFloat(st.nextToken());
					HP = Integer.parseInt(st.nextToken());
					ShotType = Integer.parseInt(st.nextToken());
					BulletSpeed = Float.parseFloat(st.nextToken());
					BulletWay = Integer.parseInt(st.nextToken());

					//拡張データを読み取る
					//現在は5つの拡張データを獲得するようにする
					for(int i =0;i < 5;i++){
						if(st.hasMoreTokens() == true){
							EX[i] = Float.parseFloat(st.nextToken());
						}else{
							//読み取るデータがない場合0を格納する
							EX[i] = 0.0f;
						}
					}

					//バッファ以上のデータがある場合は破棄する
					if(st.hasMoreTokens() == true){
						System.out.println("ファイルフォーマットが不正,以降のトークンは破棄します");
						while(st.hasMoreTokens()){
							st.nextToken();
						}
					}

					//獲得したデータを行動情報を格納する
					schedule.add(new EnemyData(AppTime,x,y,vx,vy,HP,ShotType,BulletSpeed,BulletWay,false,EX));
					flag = 0;
				}
			}

			//ファイルを閉じる
			br.close();

		} catch (FileNotFoundException e) {
			System.out.println("ファイルの読み込みに失敗");
			return false;
		} catch (IOException e) {
			System.out.println("ファイルの読み込みに失敗");
			return false;
		}
		return true;
	}
}