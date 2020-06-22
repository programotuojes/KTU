using System;
using System.Diagnostics;
using System.Threading;
using System.Threading.Tasks;

namespace L2 {
	class Program {

        #region
        static int nStep;
		static int nEnd;
		static int repeats;
		static int wBound;

		static int n;
		static int W;
		static int[] w;
		static int[] v;
		static int?[] FDynValues;
		static string s;
        #endregion

        static void Main() {
			W = 3;
			wBound = 20;

			n = 1000;
			nStep = 1000;
			nEnd = 15000;
			repeats = 2;

			Utils.Init(nEnd, W, wBound, out w, out v, out FDynValues);

			Warmup();

            //TestF();
            //TestFParallel();
            //TestMinInserts();


            for (; n <= nEnd; n += nStep) {
                Console.WriteLine("n = {0}", n);
                TestF();
                //TestFDyn();
                //TestFParallel();
            }
        }

		static void Warmup() {
			int previousN = n;
			int previousRepeats = repeats;

			n = 20;
			repeats = 3;

			Console.WriteLine("--- Warmup ---");
			Console.WriteLine("Total: {0} ticks\n\n", MeasureTime(FDyn));

			n = previousN;
			repeats = previousRepeats;
			FDynValues = new int?[FDynValues.Length];
		}

		static void TestF() {
			long time = MeasureTime(F);
			Utils.SaveResults(Utils.FResultsFile, n, time);
			Console.WriteLine("Average time: {0} ms\n\n", time);
		}

		static void TestFDyn() {

			long time = MeasureTime(FDyn);
			Utils.SaveResults(Utils.FDynResultsFile, n, time);
			Console.WriteLine("Average time: {0} ticks\n\n", time);
		}

		static void TestFParallel() {
			long time = MeasureTime(FParallel);
			Utils.SaveResults(Utils.FParallelResultsFile, n, time);
			Console.WriteLine("Average time: {0} ms\n\n", time);
		}

		static void TestMinInserts() {
			Random random = new Random();
			Stopwatch stopwatch = new Stopwatch();
			string chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
			string line = "";

			for (int i = 0; i < 600; i++) {
				line += chars[random.Next(chars.Length)];

				stopwatch.Start();
				Console.WriteLine("Result = {0}", MinInsertsDyn(line));
				stopwatch.Stop();

				Utils.SaveResults(Utils.MinInsertsFile, i, stopwatch.ElapsedTicks);
			}
		}

		static long MeasureTime(Func<int, int> function) {
			Stopwatch stopwatch = new Stopwatch();
			long time = 0;

			for (int i = 0; i < repeats; i++) {
				stopwatch.Reset();
				Console.Write("Running...");

				stopwatch.Start();
				int result = function(W);
				stopwatch.Stop();

				time += stopwatch.ElapsedMilliseconds;
				Console.WriteLine("\rResult: {0}; time: {1} ms", result, stopwatch.ElapsedMilliseconds);
				FDynValues = new int?[FDynValues.Length];
			}

			return time / repeats;
		}

		static int F(int W) {
			if (W == 0)
				return 0;

			int max = 0;

			for (int i = 0; i < n; i++)
				if (w[i] <= W)
					max = Math.Max(max, F(W - w[i]) + v[i]);

			return max;
		}

		static int FDyn(int W) {
			if (W == 0)
				return 0;

			int max = 0;

			for (int i = 0; i < n; i++) {
				if (w[i] <= W) {
					if (FDynValues[W - w[i]] == null)
						FDynValues[W - w[i]] = FDyn(W - w[i]);

					max = Math.Max(max, (int)FDynValues[W - w[i]] + v[i]);
				}
			}

			return max;
		}

		static int FParallel(int W) {
			if (W == 0)
				return 0;

			int max = 0;

			Parallel.For(0, n, i => {
				if (w[i] <= W)
					Interlocked.Exchange(ref max, Math.Max(max, F(W - w[i]) + v[i]));
			});

			return max;
		}

		static int MinInsertsDyn(string s) {
			int n = s.Length;
			int[,] table = new int[n, n];

			for (int len = 1; len < n; len++) {
				for (int l = 0, h = len; h < n; l++, h++) {
					if (s[l] == s[h]) {
						table[l, h] = table[l + 1, h - 1];
						continue;
                    }

					table[l, h] = Math.Min(table[l, h - 1], table[l + 1, h]) + 1;
                }
            }

			return table[0, n - 1];
        }
	}
}
