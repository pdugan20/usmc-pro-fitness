package com.patdugan.usmcprofitness;

import android.util.Log;

public class PFTCalculatorLogic {
	public int pullUpScore;
	public int crunchesScore;
	public int runScore;
	public int overallScore;
	
	public int GetPftInputs(int runTimeMinutes, int runTimeSeconds, int pullUpInt, int crunchesInt) {
		// Scoring for pullups
		if (pullUpInt < 3) {
			pullUpScore = 0;
		}
		if (pullUpInt == 3) {
			pullUpScore = 15;
		}
		if (pullUpInt == 4) {
			pullUpScore = 20;
		}
		if (pullUpInt == 5) {
			pullUpScore = 25;
		}
		if (pullUpInt == 6) {
			pullUpScore = 30;
		}
		if (pullUpInt == 7) {
			pullUpScore = 35;
		}
		if (pullUpInt == 8) {
			pullUpScore = 40;
		}
		if (pullUpInt == 9) {
			pullUpScore = 45;
		}
		if (pullUpInt == 10) {
			pullUpScore = 50;
		}
		if (pullUpInt == 11) {
			pullUpScore = 55;
		}
		if (pullUpInt == 12) {
			pullUpScore = 60;
		}
		if (pullUpInt == 13) {
			pullUpScore = 65;
		}
		if (pullUpInt == 14) {
			pullUpScore = 70;
		}
		if (pullUpInt == 15) {
			pullUpScore = 75;
		}
		if (pullUpInt == 16) {
			pullUpScore = 80;
		}
		if (pullUpInt == 17) {
			pullUpScore = 85;
		}
		if (pullUpInt == 18) {
			pullUpScore = 90;
		}
		if (pullUpInt == 19) {
			pullUpScore = 95;
		}
		if (pullUpInt >= 20) {
			pullUpScore = 100;
		}
		
		// Scoring for crunches
		if (crunchesInt < 40) {
			crunchesScore = 0;
		}
		else if (crunchesInt >= 40) {
			crunchesScore = crunchesInt;
		}
		
		// Scoring for run-time
		
		if (runTimeMinutes < 18) {
			runScore = 100;
			}
		
		if (runTimeMinutes == 18) {
			if (runTimeSeconds == 0) {
				runScore = 100;
			}
			if (runTimeSeconds >= 10 && runTimeSeconds < 20) {
				runScore = 99;
			}
			if (runTimeSeconds >= 20 && runTimeSeconds < 30) {
				runScore = 98;
			}
			if (runTimeSeconds >= 30 && runTimeSeconds < 40) {
				runScore = 97;
			}
			if (runTimeSeconds >= 40 && runTimeSeconds < 50) {
				runScore = 96;
			}
			if (runTimeSeconds >= 50 && runTimeSeconds < 60) {
				runScore = 95;
			}
		}
		
		if (runTimeMinutes == 19) {
			if (runTimeSeconds < 10) {
				runScore = 94;
			}
			if (runTimeSeconds >= 10 && runTimeSeconds < 20) {
				runScore = 93;
			}
			if (runTimeSeconds >= 20 && runTimeSeconds < 30) {
				runScore = 92;
			}
			if (runTimeSeconds >= 30 && runTimeSeconds < 40) {
				runScore = 91;
			}
			if (runTimeSeconds >= 40 && runTimeSeconds < 50) {
				runScore = 90;
			}
			if (runTimeSeconds >= 50 && runTimeSeconds < 60) {
				runScore = 89;
			}
		}
		
		if (runTimeMinutes == 20) {
			if (runTimeSeconds < 10) {
				runScore = 88;
			}
			if (runTimeSeconds >= 10 && runTimeSeconds < 20) {
				runScore = 87;
			}
			if (runTimeSeconds >= 20 && runTimeSeconds < 30) {
				runScore = 86;
			}
			if (runTimeSeconds >= 30 && runTimeSeconds < 40) {
				runScore = 85;
			}
			if (runTimeSeconds >= 40 && runTimeSeconds < 50) {
				runScore = 84;
			}
			if (runTimeSeconds >= 50 && runTimeSeconds < 60) {
				runScore = 83;
			}
		}
		
		if (runTimeMinutes == 21) {
			if (runTimeSeconds < 10) {
				runScore = 82;
			}
			if (runTimeSeconds >= 10 && runTimeSeconds < 20) {
				runScore = 81;
			}
			if (runTimeSeconds >= 20 && runTimeSeconds < 30) {
				runScore = 80;
			}
			if (runTimeSeconds >= 30 && runTimeSeconds < 40) {
				runScore = 79;
			}
			if (runTimeSeconds >= 40 && runTimeSeconds < 50) {
				runScore = 78;
			}
			if (runTimeSeconds >= 50 && runTimeSeconds < 60) {
				runScore = 77;
			}
		}
		
		if (runTimeMinutes == 22) {
			if (runTimeSeconds < 10) {
				runScore = 76;
			}
			if (runTimeSeconds >= 10 && runTimeSeconds < 20) {
				runScore = 75;
			}
			if (runTimeSeconds >= 20 && runTimeSeconds < 30) {
				runScore = 74;
			}
			if (runTimeSeconds >= 30 && runTimeSeconds < 40) {
				runScore = 73;
			}
			if (runTimeSeconds >= 40 && runTimeSeconds < 50) {
				runScore = 72;
			}
			if (runTimeSeconds >= 50 && runTimeSeconds < 60) {
				runScore = 71;
			}
		}
		
		if (runTimeMinutes == 23) {
			if (runTimeSeconds < 10) {
				runScore = 70;
			}
			if (runTimeSeconds >= 10 && runTimeSeconds < 20) {
				runScore = 69;
			}
			if (runTimeSeconds >= 20 && runTimeSeconds < 30) {
				runScore = 68;
			}
			if (runTimeSeconds >= 30 && runTimeSeconds < 40) {
				runScore = 67;
			}
			if (runTimeSeconds >= 40 && runTimeSeconds < 50) {
				runScore = 66;
			}
			if (runTimeSeconds >= 50 && runTimeSeconds < 60) {
				runScore = 65;
			}
		}
		
		if (runTimeMinutes >= 24) {
			runScore = 0;
		}
		
		overallScore = runScore + crunchesScore + pullUpScore;
		Log.d("RunScore", Integer.toString(runScore));
		Log.d("PullupScore", Integer.toString(pullUpScore));
		Log.d("CrunchesScore", Integer.toString(crunchesScore));
		return overallScore;
	}
}