package de.quinscape.automaton.runtime.pubsub;

import de.quinscape.automaton.runtime.filter.Filter;
import de.quinscape.automaton.runtime.ws.AutomatonClientConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Encapsulates all registrations for one topic.
 */
class Topic
{
    private final String name;

    private final Map<Object, Set<TopicRegistration>> registrationsByRecipient = new HashMap<>();


    public Topic(String name)
    {

        this.name = name;
    }


    public synchronized void subscribe(
        AutomatonClientConnection connection,
        Filter filter,
        Long id
    )
    {
        final String cid = connection.getConnectionId();
        final TopicRegistration newRegistration = new TopicRegistration(connection, filter, id);
        addRegistration(cid, newRegistration);
    }


    private void addRegistration(Object key, TopicRegistration newRegistration)
    {
        Set<TopicRegistration> registrations = registrationsByRecipient.get(key);
        if (registrations == null)
        {
            registrations = new HashSet<>();
            registrations.add(
                newRegistration
            );
            registrationsByRecipient.put(key, registrations);
        }
        else
        {
            registrations.add(newRegistration);
        }
    }


    public synchronized void subscribe(
        TopicListener topicListener,
        Filter filter,
        Long id
    )
    {
        final TopicRegistration newRegistration = new TopicRegistration(topicListener, filter, id);
        addRegistration(topicListener, newRegistration);
    }


    public synchronized void unsubscribe(AutomatonClientConnection connection, Long id)
    {
        final Set<TopicRegistration> registrations = registrationsByRecipient.get(connection.getConnectionId());
        registrations.removeIf(registration -> registration.getId().equals(id));
    }


    public synchronized void unsubscribe(TopicListener topicListener, Long id)
    {
        final Set<TopicRegistration> registrations = registrationsByRecipient.get(topicListener);
        registrations.removeIf(registration -> registration.getId().equals(id));
    }


    public String getName()
    {
        return name;
    }


    public synchronized List<Set<TopicRegistration>> getRegistrationsByConnection()
    {
        return new ArrayList<>(registrationsByRecipient.values());
    }


    @Override
    public String toString()
    {
        return super.toString() + ": "
            + "name = '" + name + '\''
            + ", connections = " + registrationsByRecipient
            ;
    }
}
