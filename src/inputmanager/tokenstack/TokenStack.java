package inputmanager.tokenstack;

import inputmanager.tokenizer.Token;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.EmptyStackException;

public class TokenStack implements Stack<Token>{
    ArrayDeque<Token> storage;
    public TokenStack(){
        storage = new ArrayDeque<>();
    }
    @Override
    public Stack<Token> push(Token token) {
        storage.push(token);
        return this;
    }

    @Override
    public boolean isEmpty() {
        return storage.isEmpty();
    }

    @Override
    public Token top() throws EmptyStackException {
        return storage.peek();
    }

    @Override
    public Token pop() throws EmptyStackException {
        return storage.pop();
    }

    @Override
    public Collection<Token> getAll() {
        return storage;
    }
    @Override
    public void clear(){
        this.storage.clear();
    }
    @Override
    public String toString(){
        StringBuilder buffer = new StringBuilder();
        for(Token t: storage){
            buffer.append(t.getSequence()).append(" ");
        }
        return buffer.toString();
    }
}