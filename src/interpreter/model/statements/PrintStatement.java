package interpreter.model.statements;
import interpreter.model.exceptions.*;
import interpreter.model.outputlist.OutputList;
import interpreter.model.programstate.ProgramState;
import interpreter.model.expressions.Expression;
import interpreter.model.symboltable.SymbolTable;
import interpreter.model.type.Type;
import interpreter.model.values.Value;

public class PrintStatement implements Statement {
    final Expression expressionToPrint;
    public PrintStatement(Expression expressionToPrint){
        this.expressionToPrint = expressionToPrint;
    }
    @Override
    public ProgramState execute(ProgramState state) throws ValueException, ExpressionException, HeapException, SymbolTableException {
        OutputList<Value> outputList  = state.getOutputList();
        if(outputList == null)
            return state;
        outputList.append(expressionToPrint.evaluate(state));
        return null;
    }

    @Override
    public SymbolTable<String, Type> typecheck(SymbolTable<String, Type> environment) throws TypecheckException {
        expressionToPrint.typecheck(environment);
        return environment;
    }

    @Override
    public Statement deepCopy() throws ExpressionException {
        return new PrintStatement(expressionToPrint.deepCopy());
    }

    @Override
    public String toString(){
        return "print( "+ expressionToPrint.toString()+" )";
    }

}
