package interpreter.model.statements.filestatements;

import interpreter.model.expressions.Expression;
import interpreter.model.filetable.FileTable;
import interpreter.model.programstate.ProgramState;
import interpreter.model.statements.Statement;
import interpreter.model.exceptions.*;
import interpreter.model.symboltable.SymbolTable;
import interpreter.model.type.Type;
import interpreter.model.values.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenReadFileStatement implements Statement {
    final Expression filenameExpression;

    public OpenReadFileStatement(Expression expression) {
        this.filenameExpression = expression;
    }


    @Override
    public String toString() {
        return "fopen( " + this.filenameExpression.toString() + " )";
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ValueException, ExpressionException, HeapException, SymbolTableException {
        FileTable fileTable = state.getFileTable();
        StringValue string = (StringValue) filenameExpression.evaluate(state);
        if (fileTable.lookup(string.getValue()) != null) {
            throw new StatementException("Trying to open a file which was already opened before -- " + string.getValue());
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(string.getValue()));
            fileTable.add(string, bufferedReader);
        } catch (FileNotFoundException e) {
            throw new StatementException(e.getMessage());
        }
        return null;
    }

    @Override
    public SymbolTable<String, Type> typecheck(SymbolTable<String, Type> environment) throws TypecheckException {
        Type type = filenameExpression.typecheck(environment);
        if (!(type.getDefault() instanceof StringValue)) {
            throw new TypecheckException("Argument given to an OpenReadFileStatement must evaluate to a string -- instead %s evaluates to a %s".formatted(filenameExpression, type));
        }
        return environment;
    }

    @Override
    public Statement deepCopy() throws ExpressionException {
        return new OpenReadFileStatement(this.filenameExpression.deepCopy());
    }
}
