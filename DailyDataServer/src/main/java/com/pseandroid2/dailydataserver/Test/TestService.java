package com.pseandroid2.dailydataserver.Test;

import com.pseandroid2.dailydataserver.onlineDatabase.DeltaDB.DeltaRepository;
import com.pseandroid2.dailydataserver.onlineDatabase.FetchRequestDB.FetchRequestRepository;
import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.ProjectParticipantsRepository;
import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.ProjectRepository;
import com.pseandroid2.dailydataserver.postDatabase.Post;
import com.pseandroid2.dailydataserver.postDatabase.PostsRepository;
import com.pseandroid2.dailydataserver.postDatabase.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Deprecated(since = "Only for Testing")
@Service
public class TestService {
    @Autowired
    private PostsRepository pr;
    @Autowired
    private TemplateRepository tr;

    @Autowired
    private DeltaRepository dr;
    @Autowired
    private FetchRequestRepository frr;
    @Autowired
    private ProjectRepository prr;
    @Autowired
    private ProjectParticipantsRepository ppr;

    @Deprecated(since = "Only for testing")
    public TestService(final PostsRepository pr, final TemplateRepository tr, final DeltaRepository dr,
                       final FetchRequestRepository frr,
                       final ProjectRepository prr, final ProjectParticipantsRepository ppr) {
        this.pr = pr;
        this.tr = tr;
        this.dr = dr;
        this.frr = frr;
        this.prr = prr;
        this.ppr = ppr;
    }

    @Deprecated(since = "Only for testing")
    public List<Integer> getAllPostFromUser(String user) {
        List<Post> postList = pr.findByCreatedBy(user);
        List<Integer> ids = new ArrayList<>();
        for (Post o : postList) {
            ids.add(o.postId);
        }
        return ids;
    }

    @Deprecated(since = "Only for testing")

    public boolean deleteAll() {
        pr.deleteAll();
        tr.deleteAll();
        dr.deleteAll();
        frr.deleteAll();
        prr.deleteAll();
        ppr.deleteAll();
        return true;
    }
}
