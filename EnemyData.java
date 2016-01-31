/*
 * 敵の行動を記録するクラス
 * csvファイルから読み取ったデータはここに格納される
 */
public class EnemyData {
	//削除フラグ
	boolean deleteFlag;
	//出現タイミング
	int appTime;
	//座標
	float x,y;
	//速度
	float vx,vy;
	//hp
	int hp;
	//弾幕タイプ
	int shotType;
	//弾速度
	float bulletSpeed;
	//弾道数
	int bulletWay;
	//拡張変数
	float exData[];
	
	EnemyData(int AppTime,float x,float y,float vx,float vy,int HP,int ShotType,float BulletSpeed,int BulletWay,boolean DeleteFlag,float EXData[]){
		this.appTime = AppTime;
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.hp = HP;
		this.shotType = ShotType;
		this.bulletSpeed = BulletSpeed;
		this.bulletWay = BulletWay;
		this.deleteFlag = DeleteFlag;
		this.exData = EXData;
	}
}