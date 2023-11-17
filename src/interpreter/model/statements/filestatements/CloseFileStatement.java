package interpreter.model.statements.filestatements;

import interpreter.model.expressions.Expression;
import interpreter.model.filetable.FileTable;
import interpreter.model.programstate.ProgramState;
import interpreter.model.statements.Statement;
import interpreter.model.types.Type;
import interpreter.model.exceptions.*;
import interpreter.model.values.StringValue;
import interpreter.model.values.Value;

import java.io.IOException;

public class CloseFileStatement implements Statement {
    private final Expression filenameExpression;

    public CloseFileStatement(Expression filenameExpression){
        this.filenameExpression = filenameExpression;
    }

    @Override
    public String toString(){
        return "fclose ( "+this.filenameExpression.toString()+" )";
    }
    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ValueException, ExpressionException, SymbolTableException, TypeException {
        Value value = filenameExpression.evaluate(state.getSymbolTable());
        if(!value.isOfType(Type.STRING))
            throw new StatementException("Files are identified by strings -- provided "+value.getType().toString());
        String fileIdentifier = ((StringValue) value).getValue();
        FileTable fileTable = state.getFileTable();
        try {
            fileTable.lookup(fileIdentifier).close();
        } catch (IOException e) {
            throw new StatementException(e.getMessage()+" -- "+fileIdentifier);
        }
        fileTable.remove(fileIdentifier);
        return state;
    }

    @Override
    public Statement deepCopy() throws ExpressionException {
        return new CloseFileStatement(this.filenameExpression.deepCopy());
    }
}