import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/*
 * ゲームの管理を行う
 * すべてのオブジェクトはこのクラスが管理する
 */
public class GameManager implements KeyListener{

	//EnemyControllerから取得した敵敵行動情報
	ArrayList<EnemyData> enemySchedule;


	//プレイヤーの弾のリスト
	static ArrayList<GameObject> playerBullets;
	//敵のリスト
	static ArrayList<GameObject> enemy;
	//敵の弾のリスト
	static ArrayList<GameObject> enemyBullets;

	//エフェクトリスト
	static ArrayList<GameObject> effect;

	//プレイヤー
	static public GameObject player;

	//行動記録に沿って敵を出すための基準時間を作る
	private long oldTime;
	private long nowTime;

	//行動情報が記述されているファイルへのパス
	String fileName;

	//ゲームの更新処理を中断させるフラグ
	private boolean stopGame;

	//ゲームマネージャの初期化を行う
	GameManager(String ParameterFile){

		playerBullets = new ArrayList<GameObject>();
		enemy = new ArrayList<GameObject>();
		enemyBullets = new ArrayList<GameObject>();
		effect = new ArrayList<GameObject>();

		player = null;
		enemySchedule = new ArrayList<EnemyData>();

		fileName = ParameterFile;
		EnemyController ec = new EnemyController();
		ec.loadFile(fileName);
		enemySchedule = ec.GetList();

		oldTime = System.nanoTime();

		nowTime = 0;
		stopGame = false;
	}

	//プレイヤーの処理を行う
	private void playerProcess()
	{
		if(player != null){
			//プレイヤーHPが0になるまで処理を行い
			//0になるとプレイヤーを消失させる
			if(player.hp > 0){
				player.process();
				player.draw();

				//プレイヤーと敵弾との判定
				for(int j = 0;j< enemyBullets.size();j++){
					GameObject e = enemyBullets.get(j);
					if(		Math.abs(e.x - player.x) <=0.01f &&
							Math.abs(e.y - player.y) <=0.01f){
						player.hp--;
						enemyBullets.remove(j);
						break;
					}
				}
			}else{
				if(player.hp == 0){
					effect.add(new ExplosionEffect(player.x,player.y,10));
					player.hp = -1;
				}
			}
		}
	}

	//エフェクト処理
	private void effectProcess()
	{
		if(effect != null){

			for(int i = 0; i < effect.size();i++){
				GameObject e = effect.get(i);

				e.process();
				e.draw();

				if(e.deleteFlag == true){
					effect.remove(i);
				}
			}
			GameMain.graphics.drawString("EffectCount =" + effect.size(), 0.01f, 0.8f);
		}
	}

	//敵の処理を行う
	private void enemyProcess()
	{
		//現在の時間を取得する
		nowTime = System.nanoTime();

		//行動情報が存在する場合に敵の出現処理を行う
		for(int i = 0;i < enemySchedule.size();i++){

			//処理を中断する
			if(stopGame == true){
				break;
			}

			//敵の行動情報を取得し出現タイミングに沿って出現させる
			EnemyData enm = enemySchedule.get(i);
			if((nowTime - oldTime)/1000000 >=enm.appTime){
				switch(enm.shotType){
				//全方位
				case 1:
					GameManager.enemy.add(new EveryShot(enm));
					enemySchedule.remove(i);
					break;
				//自機狙い
				case 2:
					GameManager.enemy.add(new AimShot(enm));
					enemySchedule.remove(i);
					break;
				//回復
				case 3:
					GameManager.enemy.add(new RecoverEnemy(enm));
					enemySchedule.remove(i);
					break;
				default:
					System.out.println("スイッチングエラー:ID=" + enm.shotType);
				}
			}
		}

		//経過時間を出力する
		GameMain.graphics.setColor(255, 0, 255);
		GameMain.graphics.drawString("count :" + (nowTime - oldTime)/1000000,20,0.01f,0.99f);

		//敵の更新描画処理をする
		for(int i = 0;i < enemy.size();i++){

			//処理を中断する
			if(stopGame == true){
				break;
			}

			//敵の配列を読み込む
			GameObject obj = enemy.get(i);
			obj.process();
			obj.draw();

			//画面外に出場合にDeleteFlagを参照しtrueの場合削除する
			if(	(obj.y > 1 || obj.y < 0 ||
				obj.x > 1 || obj.x < 0) && obj.deleteFlag == true){
				enemy.remove(i);
			}

			//プレイヤーの弾との判定
			for(int j = 0;j< playerBullets.size();j++){
				GameObject e = playerBullets.get(j);
				if(		Math.abs(e.x - obj.x) <=0.01f &&
						Math.abs(e.y - obj.y) <=0.01f){
					obj.hp--;
					if(obj.hp <=0){
						//消滅エフェクトを出す
						effect.add(new ExplosionEffect(obj.x,obj.y,20,244,242,84,15,7));
						enemy.remove(i);
					}
					playerBullets.remove(j);
					break;
				}
			}
		}
	}

	//プレイヤーの弾の処理を行う
	private void playerBulletProcess()
	{

		GameMain.graphics.drawString("PlayerShotCount =" + playerBullets.size(), 0.01f, 0.85f);

		//プレイヤー弾の更新描画処理をする
		for(int i = 0;i < playerBullets.size();i++){

			//処理を中断する
			if(stopGame == true){
				break;
			}

			GameObject obj = playerBullets.get(i);
			obj.process();
			obj.draw();

			//画面外に出た弾を削除する
			if( obj.y > 1.1 || obj.y < -0.1||
				obj.x > 1.1 || obj.x < -0.1 && obj.deleteFlag == true){
				playerBullets.remove(i);
			}
		}

	}

	//敵の弾の処理を行う
	private void enemyBulletProcess()
	{

		GameMain.graphics.drawString("EnemyShotCount =" + enemyBullets.size(), 0.01f, 0.9f);

		//敵弾の更新描画処理をする
		for(int i = 0;i < enemyBullets.size();i++){
			//処理を中断する
			if(stopGame == true){
				break;
			}

			GameObject obj = enemyBullets.get(i);
			obj.process();
			obj.draw();
			if(	obj.y > 1 || obj.y < 0 ||
				obj.x > 1 || obj.x < 0 && obj.deleteFlag == true){
				enemyBullets.remove(i);
			}
		}
	}

	//すべてのゲーム処理を行う
	public void run(){
		playerProcess();
		enemyProcess();
		playerBulletProcess();
		enemyBulletProcess();
		effectProcess();
	}


	public void keyTyped(KeyEvent e) {

	}


	public void keyPressed(KeyEvent e) {

	}


	public void keyReleased(KeyEvent e) {
		//デバッグ用のキーを設定する
		switch(e.getKeyCode()){

			case KeyEvent.VK_R:
				stopGame = true;

				//画面内のオブジェクトを削除する
				playerBullets.clear();
				enemy.clear();
				enemyBullets.clear();
				enemySchedule.clear();
				EnemyController ec = new EnemyController();
				ec.loadFile(fileName);
				enemySchedule = ec.GetList();

				//タイマーを初期化
				oldTime = System.nanoTime();
				stopGame = false;

				//プレイヤーHPを初期化する
				player.hp = Player.firstHP;
				break;
			case KeyEvent.VK_H:

				//プレイヤーHPを初期化する
				player.hp = Player.firstHP;
				break;
		}
	}
}