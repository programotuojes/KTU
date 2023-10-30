using System.Collections.Immutable;
using System.Linq;
using Microsoft.CodeAnalysis;
using Microsoft.CodeAnalysis.Diagnostics;

namespace L3
{
    [DiagnosticAnalyzer(LanguageNames.CSharp)]
    public class L3Analyzer : DiagnosticAnalyzer
    {
        public const string DiagnosticId = "L3";

        private const string Title = "Enpoints should have an authorization attribute.";
        private const string MessageFormat = "Endpoint '{0}' is not covered with [Authorize] or [AllowAnonymous]";
        private const string Description = "Endpoint is not covered with [Authorize] or [AllowAnonymous]";
        private const string Category = "Security";

        private static readonly DiagnosticDescriptor Rule = new DiagnosticDescriptor(DiagnosticId, Title, MessageFormat, Category, DiagnosticSeverity.Warning, isEnabledByDefault: true, description: Description);

        public override ImmutableArray<DiagnosticDescriptor> SupportedDiagnostics { get { return ImmutableArray.Create(Rule); } }

        public override void Initialize(AnalysisContext context)
        {
            context.ConfigureGeneratedCodeAnalysis(GeneratedCodeAnalysisFlags.None);
            context.EnableConcurrentExecution();
            context.RegisterSymbolAction(AnalyzeSymbol, SymbolKind.Method);
        }

        private static void AnalyzeSymbol(SymbolAnalysisContext context)
        {
            var parentClass = (ITypeSymbol) context.Symbol.ContainingType;

            if (parentClass == null) return;
            if (parentClass.BaseType.Name != "ControllerBase") return;

            var controllerAttributes = parentClass.GetAttributes().Select(x => x.AttributeClass.Name).ToList();
            if (controllerAttributes.Any(x => x.Contains("Authorize") || x.Contains("AllowAnonymous"))) return;

            var method = (IMethodSymbol) context.Symbol;
            var methodAttributes = method.GetAttributes().Select(x => x.AttributeClass.Name).ToList();

            if (!methodAttributes.Any(x => x.Contains("Http"))) return;
            if (methodAttributes.Any(x => x.Contains("Authorize") || x.Contains("AllowAnonymous"))) return;

            var diagnostic = Diagnostic.Create(Rule, context.Symbol.Locations[0], method.Name);
            context.ReportDiagnostic(diagnostic);
        }
    }
}
