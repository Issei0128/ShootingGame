/*
 * FPSの制御
 * FPSの計測
 * 処理時間の計測を行う
 */

public class FPSManager {
	//処理時間計測用
	private long oldTime;
	private float overTime;
	//処理時間
	private float processTime;
	//プログラムカウンタ
	private int count;
	//目標FPSを指定
	private int FPS;
	//FPSの平均を算出するための配列
	private float aveFPS[];
	
	FPSManager(int FPS){
		oldTime = System.nanoTime();
		processTime = 0.0f;
		count = 1;
		this.FPS = FPS;
		overTime = 0.0f;
		aveFPS = new float[FPS];
	}
	
	//処理の開始を記録する
	public void setTimer(){
		//処理開始時間を記録
		oldTime = System.nanoTime();
	}
	
	//1フレームの時間を返す
	public float getFrameSec(){
		return (float)1000 / FPS;
	}
	
	//FPSを固定する
	public void FPS(){
		
		//基準時間を作る
		float fps = (float)1000 / FPS;
		
		//現在の時間を取得する
		long nowTime = System.nanoTime();
		//現在までの処理時間を計算する(ms)
		processTime = (float)(nowTime - oldTime)/1000000;
		processTime -= overTime;
		//基準時間以下の場合基準時間まで待機させる
		if(processTime<fps){
			try {
				Thread.sleep((int)(fps) - (int)processTime);
			//	Thread.sleep(1);
			} catch (InterruptedException e) {
				System.out.println("FPSエラー");
			}
		}

		overTime = (fps - processTime)-(int)(fps - processTime);
	}

	
	//処理時間を表示する
	public double getProcessTime(){
		long Time = System.nanoTime();
		return ((double)(Time - oldTime)/1000000);

	}
	
	//現在のFPSを獲得する
	public float getFPS(){
		long Time = System.nanoTime();
		
		float ave = 0.0f;

		aveFPS[count%FPS] = 1000/((Time - oldTime)/1000000);
		count++;
		for(float mfps : aveFPS){
			ave += mfps;
		};
		//ナノ秒→ミリ秒に治す
		return (ave/FPS);
		
	}
	
}