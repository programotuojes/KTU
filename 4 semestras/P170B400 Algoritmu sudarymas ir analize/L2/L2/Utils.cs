using System;
using System.IO;
using System.Text;

namespace L2 {
	public static class Utils {
		private const string arraysFile = "Arrays.txt";
		public const string FResultsFile = "FResults.csv";
		public const string FDynResultsFile = "FDynResults.csv";
		public const string FParallelResultsFile = "FParallelResults.csv";
		public const string MinInsertsFile = "MinInsertResults.csv";

		public static void Init(int nEnd, int W, int randomBound, out int[] w, out int[] v, out int?[] FDynValues) {
			if (File.Exists(FResultsFile))
				File.Delete(FResultsFile);

			if (File.Exists(FDynResultsFile))
				File.Delete(FDynResultsFile);

			if (File.Exists(FParallelResultsFile))
				File.Delete(FParallelResultsFile);

			if (File.Exists(MinInsertsFile))
				File.Delete(MinInsertsFile);

			if (IsFileValid(nEnd)) {
				Read(W, out w, out v, out FDynValues);
			} else {
				Generate(nEnd, W, randomBound, out w, out v, out FDynValues);
				Write(nEnd, w, v);
			}
		}

		public static void SaveResults(string filename, int n, long time) {
			StreamWriter sw = File.AppendText(filename);
			sw.WriteLine("{0},{1}", n, time);
			sw.Close();
		}

		private static void Read(int W, out int[] w, out int[] v, out int?[] FDynValues) {
			StreamReader reader = new StreamReader(arraysFile);

			int n = int.Parse(reader.ReadLine());

			w = new int[n];
			v = new int[n];

			for (int i = 0; i < n; i++)
				w[i] = int.Parse(reader.ReadLine());

			for (int i = 0; i < n; i++)
				v[i] = int.Parse(reader.ReadLine());

			FDynValues = InitializeFDynValues(W, w);

			reader.Close();
		}

		private static void Write(int n, int[] w, int[] v) {
			StringBuilder sb = new StringBuilder();

			sb.AppendLine(n.ToString());

			foreach (int i in w)
				sb.AppendLine(i.ToString());

			foreach (int i in v)
				sb.AppendLine(i.ToString());

			File.WriteAllText(arraysFile, sb.ToString());
		}

		private static void Generate(int n, int W, int randomBound, out int[] w, out int[] v, out int?[] FDynValues) {
			w = new int[n];
			v = new int[n];

			Random random = new Random();

			for (int i = 0; i < n; i++) {
				w[i] = random.Next(1, randomBound);
				v[i] = random.Next(1, randomBound);
			}

			FDynValues = InitializeFDynValues(W, w);
		}

		private static int?[] InitializeFDynValues(int W, int[] w) {
			int minW = int.MaxValue;
			bool found = false;

			foreach (int i in w) {
				if (i <= W && minW > i) {
					minW = i;
					found = true;
				}
			}

			if (found)
				return new int?[W - minW + 1];
			else
				return null;
		}

		private static bool IsFileValid(int maxN) {
			if (!File.Exists(arraysFile))
				return false;

			StreamReader sr = new StreamReader(arraysFile);

			bool result = sr.ReadLine() == maxN.ToString();
			sr.Close();

			return result;
		}
	}
}
