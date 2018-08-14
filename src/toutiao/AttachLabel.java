package toutiao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class AttachLabel {
	public static final int[] dX = { 0, -1, -1, -1 };
	public static final int[] dY = { -1, -1, 0, 1 };

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String[] str = sc.nextLine().split(",");
		int m = Integer.parseInt(str[0]);
		int n = Integer.parseInt(str[0]);
		int[][] map = new int[m][n];
		for (int i = 0; i < m; i++) {
			String[] strs = sc.nextLine().split(",");
			for (int j = 0; j < n; j++)
				map[i][j] = Integer.parseInt(strs[j]);
		}
		sc.close();
		//int[] result=searchIce(m,n,map);
		int[] result = connectDomain(m, n, map);
		System.out.println(result[0] + "," + result[1]);
	}

	public static int[] connectDomain(int m, int n, int[][] map) {
		int[] result = new int[2];
		int[][] status = new int[m][n];// 标签图像
		int flag = 0;//标签
		int N = 0;// 标签数
		for (int x = 0; x < m; x++) {
			for (int y = 0; y < n; y++) {
				if (map[x][y] == 1) {
					int[] tempX = new int[4];
					int[] tempY = new int[4];
					for (int i = 0; i < 4; i++) {
						tempX[i] = x + dX[i];
						tempY[i] = y + dY[i];
					}
					if (x == 0 && y == 0) {
						flag = flag + 1;
						status[x][y] = flag;
						N = N + 1;
					} else if (x == 0 && y > 0) {// 第一行
						if (status[tempX[0]][tempY[0]] == 0) {
							flag = flag + 1;
							status[x][y] = flag;
							N = N + 1;
						} else if (status[tempX[0]][tempY[0]] == flag) {
							status[x][y] = flag;
						}
					} else if (x > 0 && y == 0) {//第一列
						if (status[tempX[2]][tempY[2]] == 0 && status[tempX[3]][tempY[3]] == 0) {
							flag = flag + 1;
							status[x][y] = flag;
							N = N + 1;
						} else if (status[tempX[2]][tempY[2]] == flag && status[tempX[3]][tempY[3]] == flag) {
							status[x][y] = flag;
						} else {
							Set<Integer> st=new TreeSet<>();
							int flag_p=flag;
							for (int k = 2; k < 4; k++) {
								if (status[tempX[k]][tempY[k]] != 0) {
									st.add(status[tempX[k]][tempY[k]]);
								}
							}
							for(Integer s:st) {
								flag_p = s;
								break;
							}
							status[x][y] = flag_p;
							if(st.size()==2) {
								for (int k = 2; k < 4; k++) {
									if (status[tempX[k]][tempY[k]] != 0) {
										status[tempX[k]][tempY[k]] = flag_p;
									}
								}
								N = N - 1;
							}
						}
					} else if (x > 0 && y == n - 1) {//最后一列
						if (status[tempX[0]][tempY[0]] == 0 && status[tempX[1]][tempY[1]] == 0
								&& status[tempX[2]][tempY[2]] == 0) {
							flag = flag + 1;
							status[x][y] = flag;
							N = N + 1;
						} else if (status[tempX[0]][tempY[0]] == flag && status[tempX[1]][tempY[1]] == flag
								&& status[tempX[2]][tempY[2]] == flag) {
							status[x][y] = flag;
						} else {
							Set<Integer> st=new TreeSet<>();
							int flag_p=flag;
							for (int k = 0; k < 3; k++) {
								if (status[tempX[k]][tempY[k]] != 0) {
									st.add(status[tempX[k]][tempY[k]]);
								}
							}
							for(Integer s:st) {
								flag_p = s;
								break;
							}	
							status[x][y] = flag_p;						
							if(st.size()==2) {
								for (int k = 0; k < 3; k++) {
									if (status[tempX[k]][tempY[k]] != 0) {
										status[tempX[k]][tempY[k]] = flag_p;
									}
								}
								N = N - 1;
							}
						}
					} else if (x > 0 && y > 0 && y < n - 1) {//其余的行和列
						if (status[tempX[0]][tempY[0]] == 0 && status[tempX[1]][tempY[1]] == 0
								&& status[tempX[2]][tempY[2]] == 0 && status[tempX[3]][tempY[3]] == 0) {
							flag = flag + 1;
							status[x][y] = flag;
							N = N + 1;
						} else if (status[tempX[0]][tempY[0]] == flag && status[tempX[1]][tempY[1]] == flag
								&& status[tempX[2]][tempY[2]] == flag && status[tempX[3]][tempY[3]] == flag) {
							status[x][y] = flag;
						} else {
							Set<Integer> st=new TreeSet<>();
							int flag_p=flag;
							for (int k = 0; k < 4; k++) {
								if (status[tempX[k]][tempY[k]] != 0) {
									st.add(status[tempX[k]][tempY[k]]);								
								}
							}
							for(Integer s:st) {
								flag_p = s;
								break;
							}			
							status[x][y] = flag_p;							
							if(st.size()==2) {
								for (int k = 0; k < 4; k++) {
									if (status[tempX[k]][tempY[k]] != 0) {
										status[tempX[k]][tempY[k]] = flag_p;
									}
								}
								N = N - 1;
							}
						}
					}
					
				} 
			}
		}	
		ArrayList<Integer> re = new ArrayList<>();
		if(N!=flag) {//已贴标签是不连续编号，需重新进行映射编码
			Map<Integer, ArrayList<Integer>> tagMap = new HashMap<>();
			for(int i=1;i<=flag;i++) {
				ArrayList<Integer> list = new ArrayList<>();
				for (int x = 0; x < m; x++) {
					for (int y = 0; y < n; y++) {
						if(status[x][y]==i) {
							list.add(x);
							list.add(y);												
						}								
					}
				}
				if(list.size()!=0) {
					tagMap.put(i, list);
					re.add(list.size()/2);
				}		
			}
			int count=1;
			for (int key : tagMap.keySet()) {				
				if(count!=key) {
					ArrayList<Integer> tempValue=tagMap.get(key);
					for(int i=0;i<tempValue.size()/2;i++) {
						status[tempValue.get(2*i)][tempValue.get(2*i+1)]=count;
					}			
				}
				count++;			
			}					
		}else {
			for (int i = 1; i <= flag; i++) {
				ArrayList<Integer> list = new ArrayList<>();
				for (int x = 0; x < m; x++) {
					for (int y = 0; y < n; y++) {
						if (status[x][y] == i) {
							list.add(x);
							list.add(y);
						}
					}
				}
				re.add(list.size() / 2);
			}
		}
		System.out.println("映射编码之后的结果:");
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(status[i][j] + " ");
			}
			System.out.println();
		}
		Collections.sort(re);
		result[0]=re.size();//标签的个数
		result[1]=re.get(re.size()-1);//标签中数目最多的元素的个数
		return result;
	}

	public static int[] searchIce(int m, int n, int[][] map) {
		int[] result = new int[2];
		int[][] status = new int[m][n];
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				status[i][j] = Integer.MAX_VALUE;//
		Map<Integer, ArrayList<Integer>> tagMap = new HashMap<>();
		int flag = 1;
		for (int x = 0; x < m; x++) {
			for (int y = 0; y < n; y++) {
				if (map[x][y] == 0)
					continue;

				int tag = Integer.MAX_VALUE;//
				for (int i = 0; i < 4; i++) {
					int tmpX = x + dX[i];
					int tmpY = y + dY[i];
					if (tmpX < 0 || tmpX >= m || tmpY < 0 || tmpY >= n || map[tmpX][tmpY] == 0)
						continue;
					tag = Math.min(tag, status[tmpX][tmpY]);
				}
				if (tag == Integer.MAX_VALUE) {//
					status[x][y] = flag;
					ArrayList<Integer> list = new ArrayList<>();
					list.add(x);
					list.add(y);
					tagMap.put(flag++, list);
				} else {
					status[x][y] = tag;
					tagMap.get(tag).add(x);
					tagMap.get(tag).add(y);
					for (int i = 0; i < 4; i++) {
						int tmpX = x + dX[i];
						int tmpY = y + dY[i];
						if (tmpX < 0 || tmpX >= m || tmpY < 0 || tmpY >= n || map[tmpX][tmpY] == 0)
							continue;
						if (status[tmpX][tmpY] != tag) {
							ArrayList<Integer> list = tagMap.get(status[tmpX][tmpY]);
							tagMap.get(tag).addAll(list);
							tagMap.remove(status[tmpX][tmpY]);
							for (int j = 0; j < list.size() / 2; j++)
								status[list.get(j * 2)][list.get(j * 2 + 1)] = tag;
						}
					}
				}
			}
		}
		int count=1;
		for (int key : tagMap.keySet()) {				
			if(count!=key) {
				ArrayList<Integer> tempValue=tagMap.get(key);
				for(int i=0;i<tempValue.size()/2;i++) {
					status[tempValue.get(2*i)][tempValue.get(2*i+1)]=count;
				}			
			}
			count++;			
		}	
		ArrayList<Integer> re = new ArrayList<>();
		for (int key : tagMap.keySet()) {
			re.add(tagMap.get(key).size() / 2);
		}
		int temp = re.get(0);
		for (int i = 0; i < re.size(); i++) {
			if (re.get(i) >= temp) {
				temp = re.get(i);
			}
		}
		result[0] = tagMap.size();
		result[1] = temp;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if(status[i][j]==Integer.MAX_VALUE) {
					status[i][j]=0;
				}
				System.out.print(status[i][j] + " ");
			}
			System.out.println();
		}
		return result;
	}

}
/*----测试案例-----
10,10 
0,0,0,0,0,0,0,0,0,0
0,0,0,1,1,0,1,0,0,0
0,1,0,0,0,0,0,1,0,1
1,0,0,0,0,0,0,0,1,1
0,0,0,1,1,1,0,0,0,1
0,0,0,0,0,0,1,0,1,1
0,1,1,0,0,0,0,0,0,0
0,0,0,1,0,1,0,0,0,0
0,0,1,0,0,1,0,0,0,0
0,1,0,0,0,0,0,0,0,0

10,10 
0,0,0,0,0,0,0,0,0,0
0,0,0,1,1,0,1,0,0,0
0,1,0,0,0,0,0,1,0,0
1,0,0,0,0,0,0,0,1,1
0,0,0,1,1,1,0,0,0,1
0,0,0,0,0,0,1,0,0,1
0,1,1,0,0,0,0,0,0,0
0,0,0,1,0,1,0,0,0,0
0,0,1,0,0,1,0,0,0,0
0,1,0,0,0,0,0,0,0,0

10,10 
1,0,0,0,0,0,0,0,0,0
0,0,0,1,1,0,1,0,0,0
0,1,0,0,0,0,0,1,0,1
1,0,0,0,0,0,0,0,1,1
0,0,0,1,1,1,0,0,0,1
0,0,0,0,0,0,1,0,1,1
0,1,1,0,0,0,0,0,0,0
0,0,0,1,0,1,0,0,0,0
0,0,1,0,0,1,0,0,0,0
0,1,0,0,0,0,0,0,0,0

10,10 
1,0,0,0,0,0,0,0,0,0
0,1,0,1,1,0,1,0,0,0
0,1,0,0,0,0,0,1,0,1
1,0,0,0,0,0,0,0,1,1
0,0,0,1,1,1,0,0,0,1
0,0,0,0,0,0,1,0,1,1
0,1,1,0,0,0,0,0,0,0
0,0,0,1,0,1,0,0,0,0
0,0,1,0,0,1,0,0,0,0
0,1,0,0,0,0,0,0,0,0

10,10 
1,1,0,0,0,0,0,0,0,0
0,0,0,1,1,0,1,0,0,0
0,1,0,0,0,0,0,1,0,1
1,0,0,0,0,0,0,0,1,1
0,0,0,1,1,1,0,0,0,1
0,0,0,0,0,0,1,0,1,1
0,1,1,0,0,0,0,0,0,0
0,0,0,1,0,1,0,0,0,0
0,0,1,0,0,1,0,0,0,0
0,1,0,0,0,0,0,0,0,0
 */